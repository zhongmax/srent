package com.csmaxwell.srent.wx.web;

import com.csmaxwell.srent.core.util.ResponseUtil;
import com.csmaxwell.srent.db.domain.SrentAddress;
import com.csmaxwell.srent.db.domain.SrentGoods;
import com.csmaxwell.srent.db.service.SrentAddressService;
import com.csmaxwell.srent.db.service.SrentGoodsService;
import com.csmaxwell.srent.wx.annotation.LoginUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户购买服务
 */
@RestController
@RequestMapping("/wx/buy")
@Validated
public class WxBuyController {

    private final Log logger = LogFactory.getLog(WxBuyController.class);

    @Autowired
    private SrentGoodsService goodsService;

    @Autowired
    private SrentAddressService addressService;

    /**
     * 购买前检查
     *
     * @param userId    用户ID
     * @param goodsId   商品ID
     * @param addressId 收货地址ID
     *                  如果收货地址为空，则查询当前用户的默认地址.
     * @return 检查结果
     */
    @GetMapping("checkout")
    public Object checkout(@LoginUser Integer userId, Integer goodsId, Integer addressId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        // 收货地址
        SrentAddress checkedAddress = null;
        if (addressId == null || addressId.equals(0)) {
            checkedAddress = addressService.findDefault(userId);
            // 仍没有地址，则没有收货地址
            // 返回一个空的地址id, 前端提示添加地址
            if (checkedAddress == null) {
                checkedAddress = new SrentAddress();
                checkedAddress.setId(0);
                addressId = 0;
            } else {
                addressId = checkedAddress.getId();
            }

        } else {
            checkedAddress = addressService.query(userId, addressId);

            if (checkedAddress == null) {
                return ResponseUtil.badArgumentValue();
            }
        }

        // 确认商品信息
        List<SrentGoods> checkedGoodsList = null;
        if (goodsId == null || goodsId.equals(0)) {
            return ResponseUtil.badArgumentValue();
        } else {
            SrentGoods goodsList = goodsService.findById(goodsId);
            //System.out.println(goodsList);
            if (goodsList == null) {
                return ResponseUtil.badArgumentValue();
            }

        }

        Map<String, Object> data = new HashMap<>();
        data.put("addressId", addressId);
        data.put("goodsId", goodsId);
        data.put("checkedAddress", checkedAddress);

        return ResponseUtil.ok(data);

    }
}
