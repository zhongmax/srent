package com.csmaxwell.srent.wx.web;

import com.csmaxwell.srent.core.system.SystemConfig;
import com.csmaxwell.srent.core.util.ResponseUtil;
//import com.csmaxwell.srent.db.domain.SrentBrand;
//import com.csmaxwell.srent.db.service.SrentBrandService;
import com.csmaxwell.srent.db.service.SrentCategoryService;
import com.csmaxwell.srent.db.service.SrentGoodsService;
//import com.csmaxwell.srent.db.service.SrentTopicService;
import com.csmaxwell.srent.wx.annotation.LoginUser;
import com.csmaxwell.srent.wx.service.HomeCacheManager;
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
import java.util.concurrent.*;

/**
 * 首页服务
 */
@RestController
@RequestMapping("/wx/home")
@Validated
public class WxHomeController {

    private final Log logger = LogFactory.getLog(WxHomeController.class);

    @Autowired
    private SrentGoodsService goodsService;

    @Autowired
    private SrentCategoryService categoryService;

//    @Autowired
//    private SrentBrandService brandService;

//    @Autowired
//    private SrentTopicService topicService;

    private final static ArrayBlockingQueue<Runnable> WORK_QUEUE =
            new ArrayBlockingQueue<>(9);

    private final static RejectedExecutionHandler HANDLER =
            new ThreadPoolExecutor.CallerRunsPolicy();

    private static ThreadPoolExecutor executorService =
            new ThreadPoolExecutor(9, 9, 1000, TimeUnit.MILLISECONDS,
                    WORK_QUEUE, HANDLER);

    @GetMapping("/cache")
    public Object cache(@NotNull String key) {
        if (!key.equals("srent_cache")) {
            return ResponseUtil.fail();
        }

        // 清除缓存
        HomeCacheManager.clearAll();
        return ResponseUtil.ok("缓存已清除");
    }

    @GetMapping("/index")
    public Object index(@LoginUser Integer userId) {
        // 优先从缓存中读取
        if (HomeCacheManager.hasData(HomeCacheManager.INDEX)) {
            return ResponseUtil.ok(HomeCacheManager.getCacheData(HomeCacheManager.INDEX));
        }
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Callable<List> allGoodsListCallable = () -> goodsService.queryByAll(0, SystemConfig.getAllLimit());

        Callable<List> channelListCallable = () -> categoryService.queryChannel();

        FutureTask<List> allGoodsListTask = new FutureTask<>(allGoodsListCallable);
        FutureTask<List> channelTask = new FutureTask<>(channelListCallable);

        executorService.submit(allGoodsListTask);
        executorService.submit(channelTask);

        Map<String, Object> entity = new HashMap<>();
        try {
            entity.put("allGoodsList", allGoodsListTask.get());
            entity.put("channel", channelTask.get());

            //缓存数据
            HomeCacheManager.loadData(HomeCacheManager.INDEX, entity);
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally {
            executorService.shutdown();
        }
        return ResponseUtil.ok(entity);
    }
}
