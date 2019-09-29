package com.csmaxwell.srent.wx.web;

import com.csmaxwell.srent.core.validator.Order;
import com.csmaxwell.srent.core.validator.Sort;
import com.csmaxwell.srent.wx.annotation.LoginUser;
import com.csmaxwell.srent.wx.service.WxOrderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/wx/order")
@Validated
public class WxOrderController {
        private final Log logger = LogFactory.getLog(WxOrderController.class);

        @Autowired
    private WxOrderService wxOrderService;

    /**
     * 订单列表
     *
     * @param userId   用户ID
     * @param showType 订单信息
     * @param page     分页页数
     * @param limit     分页大小
     * @return 订单列表
     */
    @GetMapping("list")
    public Object list(@LoginUser Integer userId,
                       @RequestParam(defaultValue = "0") Integer showType,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return wxOrderService.list(userId, showType, page, limit, sort, order);
    }

    /**
     * 获取发布的
     * @param userId 用户表ID
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return 发布信息
     */
    @GetMapping("post")
    public Object post(@LoginUser Integer userId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return wxOrderService.post(userId, page, limit, sort, order);
    }

    /**
     * 订单详情
     * @param userId 用户ID
     * @param orderId 订单ID
     * @return 订单详情
     */
    @GetMapping("detail")
    public Object rent(@LoginUser Integer userId, @NotNull Integer orderId) {
        return wxOrderService.detail(userId, orderId);
    }

    /**
     * 提交订单
     *
     * @param userId 用户ID
     * @param body 订单信息
     *             {
     *              postUserId: xxx,
     *              goodsId: xxx,
     *              days: xxx,
     *              addressId: xxx,
     *              goodsTotalPrice: xxx,
     *              freightPrice: xxx,
     *              orderTotalPrice: xxx,
     *             }
     */
    @PostMapping("submit")
    public Object submit(@LoginUser Integer userId, @RequestBody String body) {
        return wxOrderService.submit(userId, body);
    }

    @PostMapping("ship")
    public Object ship(@LoginUser Integer userId, @RequestBody String body) {
        return wxOrderService.ship(userId, body);
    }

    @PostMapping("cancel")
    public Object cancel(@LoginUser Integer userId, @RequestBody String body) {
        return wxOrderService.cancel(userId, body);
    }

    @PostMapping("rent")
    public Object rent(@LoginUser Integer userId, @RequestBody String body) {
        return wxOrderService.rent(userId, body);
    }

    @PostMapping("ritem")
    public Object ritem(@LoginUser Integer userId, @RequestBody String body) {
        return wxOrderService.ritem(userId, body);
    }

}
