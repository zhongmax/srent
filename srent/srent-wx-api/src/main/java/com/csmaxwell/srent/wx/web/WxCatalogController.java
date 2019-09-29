package com.csmaxwell.srent.wx.web;

import com.csmaxwell.srent.core.system.SystemConfig;
import com.csmaxwell.srent.core.util.ResponseUtil;
import com.csmaxwell.srent.db.domain.SrentCategory;
import com.csmaxwell.srent.db.service.SrentCategoryService;
import com.csmaxwell.srent.wx.annotation.LoginUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 类目服务
 */
@RestController
@RequestMapping("wx/catalog")
@Validated
public class WxCatalogController {
    private final Log logger = LogFactory.getLog(WxCatalogController.class);

    ExecutorService executorService = Executors.newFixedThreadPool(10);


    @Autowired
    private SrentCategoryService categoryService;

    /**
     * 分类详情
     *
     * @param id 分类ID
     *           如果为空，则为第一个
     * @return 分类详情
     */
    @GetMapping("index")
    public Object index(Integer id) {

        // 一级目录
        List<SrentCategory> l1CatList = categoryService.queryL1();

        // 当前一级目录
        SrentCategory currentCategory = null;

        if (id != null) {
            currentCategory = categoryService.findById(id);
        } else {
            currentCategory = l1CatList.get(0);
        }

        // 当前一级目录下对应的二级目录
        List<SrentCategory> currentSubCategory = null;
        if (currentCategory != null) {
            currentSubCategory = categoryService.queryByPid(currentCategory.getId());
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("categoryList", l1CatList);
        data.put("currentCategory", currentCategory);
        data.put("currentSubCategory", currentSubCategory);
        return ResponseUtil.ok(data);
    }

    /**
     * 当前分类栏目
     *
     * @param id 分类ID
     * @return 当前分类数据
     */
    @GetMapping("current")
    public Object current(@NotNull Integer id) {

        // 当前分类
        SrentCategory currentCategory = categoryService.findById(id);

        if (currentCategory == null) {
            return ResponseUtil.badArgumentValue();
        }

        List<SrentCategory> currentSubCategory = categoryService.queryByPid(currentCategory.getId());

        HashMap<String, Object> data = new HashMap<>();
        data.put("currentCategory", currentCategory);
        data.put("currentSubCategory", currentSubCategory);
        return ResponseUtil.ok(data);
    }

    @GetMapping("list")
    public Object list(@LoginUser Integer userId) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Callable<List> allCategoryListCallable = () -> categoryService.queryByAll();
        FutureTask<List> allCategoryListTask = new FutureTask<>(allCategoryListCallable);
        executorService.submit(allCategoryListTask);
        Map<String, Object> entity = new HashMap<>();
        try {
            entity.put("allCategoryList", allCategoryListTask.get());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseUtil.ok(entity);
    }
}
