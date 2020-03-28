package com.csmaxwell.srent.wx.web;

import com.csmaxwell.srent.core.util.JacksonUtil;
import com.csmaxwell.srent.core.util.ResponseUtil;
import com.csmaxwell.srent.core.validator.Order;
import com.csmaxwell.srent.core.validator.Sort;
import com.csmaxwell.srent.db.domain.SrentCategory;
import com.csmaxwell.srent.db.domain.SrentCollect;
import com.csmaxwell.srent.db.domain.SrentGoods;
import com.csmaxwell.srent.db.service.SrentCollectService;
import com.csmaxwell.srent.db.service.SrentGoodsService;
import com.csmaxwell.srent.wx.annotation.LoginUser;
import jodd.util.ObjectUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户收藏服务
 */
@RestController
@RequestMapping("/wx/collect")
@Validated
public class WxCollectController {

    private final Log logger = LogFactory.getLog(WxCollectController.class);

    @Autowired
    private SrentCollectService collectService;
    @Autowired
    private SrentGoodsService goodsService;

    /**
     * 用户收藏添加或删除
     * <p>
     * 如果商品没有收藏，则添加收藏；如果商品已经收藏，则删除收藏状态。
     * </p>
     *
     * @param userId 用户ID
     * @param body   请求内容，{ type: xxx, valueId: xxx }
     * @return 操作结果
     */
    @PostMapping("addordelete")
    public Object addordelete(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
//        System.out.println("收藏测试");
        Byte type = JacksonUtil.parseByte(body, "type");
        Integer valueId = JacksonUtil.parseInteger(body, "valueId");
//        System.out.println("valueId = " + valueId);
        if (!ObjectUtils.allNotNull(type, valueId)) {
            return ResponseUtil.badArgument();
        }

        SrentCollect collect = collectService.queryByTypeAndValue(userId, type, valueId);

        if (collect != null) {
            collectService.deleteById(collect.getId());
        } else {
            collect = new SrentCollect();
            collect.setUserId(userId);
            collect.setValueId(valueId);
            collect.setType(type);
            collectService.add(collect);
        }

        return ResponseUtil.ok();
    }

    /**
     * 用户收藏列表
     *
     * @param userId 用户ID
     * @param page   分页
     * @param limit  大小
     * @return 收藏列表
     */
    @GetMapping("list")
    public Object list(@LoginUser Integer userId,
                       @NotNull Byte type,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        List<SrentCollect> collectList = collectService.queryByType(userId, type, sort, order);
//        System.out.println(collectList);
        List<Object> collects = new ArrayList<>(collectList.size());
        for (SrentCollect collect : collectList) {
            Map<String, Object> c = new HashMap<>();
            c.put("id", collect.getId());
            c.put("type", collect.getType());
            c.put("valueId", collect.getValueId());
            c.put("userId", collect.getUserId());
            //System.out.println(collect.getValueId());
            SrentGoods goods = goodsService.findById(collect.getValueId());
//            System.out.println(goods);
            c.put("name", goods.getName());
            c.put("picUrl", goods.getGallery());
            c.put("desc", goods.getDesc());
            c.put("rentPrice", goods.getRentPrice());

            collects.add(c);
        }
        return ResponseUtil.okList(collects, collectList);
    }
}
