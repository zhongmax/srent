package com.csmaxwell.srent.wx.web;


import com.csmaxwell.srent.core.util.ResponseUtil;
import com.csmaxwell.srent.db.domain.SrentKeyword;
import com.csmaxwell.srent.db.domain.SrentSearchHistory;
import com.csmaxwell.srent.db.service.SrentKeywordService;
import com.csmaxwell.srent.db.service.SrentSearchHistoryService;
import com.csmaxwell.srent.wx.annotation.LoginUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品搜索服务
 */
@RestController
@RequestMapping("wx/search")
@Validated
public class WxSearchController {
    private final Log logger = LogFactory.getLog(WxSearchController.class);

    @Autowired
    private SrentKeywordService keywordService;
    @Autowired
    private SrentSearchHistoryService searchHistoryService;

    /**
     * 搜索页面信息
     * <p>
     *     如果用户登录，则给出用户历史搜索记录
     *     未登录，则不显示历史搜索记录
     * </p>
     *
     * @param userId 用户ID, 可选
     * @return 搜索页面信息
     */
    @GetMapping("index")
    public Object index(@LoginUser Integer userId) {
        // 取出输入框默认的关键词
        SrentKeyword defaultKeyword = keywordService.queryDefault();
        // 取出热门搜索
        List<SrentKeyword> hotKeywordList = keywordService.queryHots();
        // 历史记录
        List<SrentSearchHistory> historyList = null;
        if (userId != null) {
            // 取出用户历史关键字
            historyList = searchHistoryService.queryByUid(userId);
        } else {
            historyList = new ArrayList<>(0);
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("defaultKeyword", defaultKeyword);
        data.put("historyKeywordList", historyList);
        data.put("hotKeywordList", hotKeywordList);
        return ResponseUtil.ok(data);
    }

    /**
     * 关键字提醒
     * <p>
     * 当用户输入关键字一部分时，可以推荐系统中合适的关键字。
     *
     * @param keyword 关键字
     * @return 合适的关键字
     */
    @GetMapping("helper")
    public Object helper(@NotEmpty String keyword,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer limit) {
        List<SrentKeyword> keywordsList = keywordService.queryByKeyword(keyword, page, limit);
        String[] keys = new String[keywordsList.size()];
        int index = 0;
        for (SrentKeyword key : keywordsList) {
            keys[index++] = key.getKeyword();
        }
        return ResponseUtil.ok(keys);
    }

    /**
     * 清除用户搜索历史
     *
     * @param userId 用户ID
     * @return 清理是否成功
     */
    @PostMapping("clearhistory")
    public Object clearhistory(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        searchHistoryService.deleteByUid(userId);
        return ResponseUtil.ok();
    }
}
