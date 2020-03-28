package com.csmaxwell.srent.wx.web;

import com.csmaxwell.srent.core.util.JacksonUtil;
import com.csmaxwell.srent.core.util.ResponseUtil;
import com.csmaxwell.srent.db.domain.SrentFootprint;
import com.csmaxwell.srent.db.domain.SrentGoods;
import com.csmaxwell.srent.db.service.SrentFootprintService;
import com.csmaxwell.srent.db.service.SrentGoodsService;
import com.csmaxwell.srent.wx.annotation.LoginUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户访问足迹服务
 */
@RestController
@RequestMapping("/wx/footprint")
@Validated
public class WxFootprintController {
    private final Log logger = LogFactory.getLog(WxFootprintController.class);

    @Autowired
    private SrentFootprintService footprintService;
    @Autowired
    private SrentGoodsService goodsService;

    /**
     * 用户足迹列表
     *
     * @param page  分页页数
     * @param limit 分页大小
     * @return 用户足迹列表
     */
    @GetMapping("list")
    public Object list(@LoginUser Integer userId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        System.out.println("用户ID是" + userId);
        List<SrentFootprint> footprintList = footprintService.queryByAddTime(userId, page, limit);
        System.out.println("足迹LIST为" + footprintList);
        List<Object> footprintVoList = new ArrayList<>(footprintList.size());
        for (SrentFootprint footprint : footprintList) {
            if (goodsService.checkExistByGoodsId(footprint.getGoodsId())) {
                Map<String, Object> c = new HashMap<String, Object>();
                c.put("id", footprint.getId());
                c.put("goodsId", footprint.getGoodsId());
                c.put("addTime", footprint.getAddTime());

                SrentGoods goods = goodsService.findById(footprint.getGoodsId());
                c.put("name", goods.getName());
                c.put("picUrl", goods.getGallery());
                c.put("retailPrice", goods.getRentPrice());

                footprintVoList.add(c);
            }
        }
        return ResponseUtil.okList(footprintVoList, footprintList);
    }

    /**
     * 删除用户足迹
     *
     * @param userId 用户ID
     * @param body   请求内容， { id: xxx }
     * @return 删除操作结果
     */
    @PostMapping("delete")
    public Object delete(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (body == null) {
            return ResponseUtil.badArgument();
        }

        Integer footprintId = JacksonUtil.parseInteger(body, "id");
        if (footprintId == null) {
            return ResponseUtil.badArgument();
        }
        SrentFootprint footprint = footprintService.findById(footprintId);

        if (footprint == null) {
            return ResponseUtil.badArgumentValue();
        }
        if (!footprint.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }

        footprintService.deleteById(footprintId);
        return ResponseUtil.ok();
    }
}
