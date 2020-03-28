package com.csmaxwell.srent.admin.service;


import com.csmaxwell.srent.core.util.JacksonUtil;
import com.csmaxwell.srent.core.util.ResponseUtil;
import com.csmaxwell.srent.db.domain.SrentOrder;
import com.csmaxwell.srent.db.domain.SrentOrderGoods;
import com.csmaxwell.srent.db.domain.UserVo;
import com.csmaxwell.srent.db.service.SrentOrderGoodsService;
import com.csmaxwell.srent.db.service.SrentOrderService;
import com.csmaxwell.srent.db.service.SrentUserService;
import com.csmaxwell.srent.db.util.OrderUtil;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.csmaxwell.srent.admin.util.AdminResponseCode.ORDER_CONFIRM_NOT_ALLOWED;

/**
 * Created by maxwell on 2019/8/23
 */
@Service
public class AdminOrderService {
    private final Log logger = LogFactory.getLog(AdminOrderService.class);

    @Autowired
    private SrentOrderGoodsService orderGoodsService;
    @Autowired
    private SrentOrderService orderService;
    @Autowired
    private SrentUserService userService;
    @Autowired
    private LogHelper logHelper;

    public Object list(Integer userId, String orderSn, List<Short> orderStatusArray,
                       Integer page, Integer limit, String sort, String order) {
        List<SrentOrder> orderList = orderService.querySelective(userId, orderSn, orderStatusArray, page, limit,
                sort, order);
        return ResponseUtil.okList(orderList);
    }

    public Object detail(Integer id) {
        SrentOrder order = orderService.findById(id);
        List<SrentOrderGoods> orderGoods = orderGoodsService.queryByOid(id);
        UserVo user = userService.findUserVoById(order.getUserId());
        Map<String, Object> data = new HashMap<>();
        data.put("order", order);
        data.put("orderGoods", orderGoods);
        data.put("user", user);
        System.out.println("用户名字：" + user.getNickName());

        return ResponseUtil.ok(data);
    }
}
