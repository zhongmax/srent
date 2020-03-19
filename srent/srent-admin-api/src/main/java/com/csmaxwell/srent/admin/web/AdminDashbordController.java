package com.csmaxwell.srent.admin.web;

import com.csmaxwell.srent.core.util.ResponseUtil;
import com.csmaxwell.srent.db.service.SrentCategoryService;
import com.csmaxwell.srent.db.service.SrentGoodsService;
import com.csmaxwell.srent.db.service.SrentOrderService;
import com.csmaxwell.srent.db.service.SrentUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by maxwell on 2019/8/23
 */
@RestController
@RequestMapping("/admin/dashboard")
@Validated
public class AdminDashbordController {
    private final Log logger = LogFactory.getLog(AdminDashbordController.class);

    @Autowired
    private SrentUserService userService;
    @Autowired
    private SrentGoodsService goodsService;
    @Autowired
    private SrentOrderService orderService;
    @Autowired
    private SrentCategoryService categoryService;

    @GetMapping("")
    public Object info() {
        int userTotal = userService.count();
        int goodsTotal = goodsService.count();
        int categoryTotal = categoryService.count();
        int orderTotal = orderService.count();
        Map<String, Integer> data = new HashMap<>();
        data.put("userTotal", userTotal);
        data.put("goodsTotal", goodsTotal);
        data.put("categoryTotal", categoryTotal);
        data.put("orderTotal", orderTotal);

        return ResponseUtil.ok(data);
    }
}
