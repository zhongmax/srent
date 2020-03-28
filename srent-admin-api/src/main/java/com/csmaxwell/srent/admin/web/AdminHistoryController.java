package com.csmaxwell.srent.admin.web;

import com.csmaxwell.srent.admin.annotation.RequiresPermissionsDesc;
import com.csmaxwell.srent.core.util.ResponseUtil;
import com.csmaxwell.srent.core.validator.Order;
import com.csmaxwell.srent.core.validator.Sort;
import com.csmaxwell.srent.db.domain.SrentSearchHistory;
import com.csmaxwell.srent.db.service.SrentSearchHistoryService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by maxwell on 2019/8/23
 */
@RestController
@RequestMapping("/admin/history")
public class AdminHistoryController {
    private final Log logger = LogFactory.getLog(AdminHistoryController.class);

    @Autowired
    private SrentSearchHistoryService searchHistoryService;

    @RequiresPermissions("admin:history:list")
    @RequiresPermissionsDesc(menu = {"用户管理", "搜索历史"}, button = "查询")
    @GetMapping("/list")
    public Object list(String userId, String keyword,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<SrentSearchHistory> historyList = searchHistoryService.querySelective(userId, keyword, page, limit,
                sort, order);
        return ResponseUtil.okList(historyList);
    }
}
