package com.csmaxwell.srent.wx.service;

import com.csmaxwell.srent.core.util.JacksonUtil;
import com.csmaxwell.srent.core.util.ResponseUtil;
import com.csmaxwell.srent.db.domain.*;
import com.csmaxwell.srent.db.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

@Service
public class WxOrderService {
    private final Log logger = LogFactory.getLog(WxOrderService.class);

    @Autowired
    private SrentUserService userService;
    @Autowired
    private SrentOrderService orderService;
    @Autowired
    private SrentOrderGoodsService orderGoodsService;
    @Autowired
    private SrentAddressService addressService;
    @Autowired
    private SrentGoodsService goodsService;

    /**
     * 获取发布信息
     *
     * @param userId 用户ID
     * @return 发布信息
     */
    public Object post(Integer userId, Integer page, Integer limit, String sort, String order) {
        //SrentGoods goodsList = goodsService.findByUserId(userId);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Callable<List> allGoodsListCallable = () -> goodsService.findByUserId(userId);
        FutureTask<List> goodsList = new FutureTask<>(allGoodsListCallable);
        executorService.submit(goodsList);
        Map<String, Object> entity = new HashMap<>();
        try {
            entity.put("goodsList", goodsList.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseUtil.ok(entity);
    }


    /**
     * 订单列表
     *
     * @param userId   用户ID
     * @param showType 订单信息：
     *                 0: 我发布的
     *                 1: 我出租的
     *                 2: 我租到的
     * @param page     分页页数
     * @param limit    分页大小
     * @return 订单列表
     */
    public Object list(Integer userId, Integer showType, Integer page, Integer limit, String sort, String order) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        List<Short> orderStatus = new ArrayList<Short>(2);
        List<SrentOrder> orderList = null;

        if (showType == 0) {
            orderStatus.add((short) 1);
            orderList = orderService.queryByOrderStatus(userId, sort, order);
        } else {
            orderStatus.add((short) 1);
            orderList = orderService.queryByOrderPostStatus(userId, sort, order);
        }

//        System.out.println(orderList);
//        System.out.println("orderList有几个" + orderList.size());
        List<Map<String, Object>> orderVoList = new ArrayList<>(orderList.size());
        for (SrentOrder o : orderList) {
                if (o.getOrderStatus() == 7 && showType == 1) {
                continue;
            }
            Map<String, Object> orderVo = new HashMap<>();
            orderVo.put("id", o.getId());

            // 添加到期标志
            LocalDate then = LocalDate.now();
            boolean isExpiry = false;

            SrentOrderGoods orderGoodsDate = orderGoodsService.queryByOidDays(o.getId());
//            System.out.println(orderGoodsDate.getDays());
//            System.out.println(orderGoodsDate.getAddTime());

            LocalDate time = orderGoodsDate.getAddTime().toLocalDate();
            // System.out.println("当前日期是:" + time);
            Period p = Period.ofDays(orderGoodsDate.getDays());
            then = time.plus(p);
//            System.out.println(then);
//            isExpiry = !(then.isAfter(LocalDate.now()));
            // 判断是否到期
            isExpiry = !(then.isAfter(LocalDate.now()));

            // 测试数据
//            isExpiry = !(then.isAfter(LocalDate.of(2019, 9, 27)));

//            System.out.println(isExpiry);

            orderVo.put("isExpiry", isExpiry);
            orderVo.put("date", then);

            orderVo.put("orderSn", o.getOrderSn());
            orderVo.put("orderPrice", o.getOrderPrice());
            if (o.getShipSn() == null && o.getShipChannel() == null) {
                orderVo.put("hasShip", false);
            } else {
                orderVo.put("hasShip", true);
                orderVo.put("orderShipSn", o.getShipSn());
                orderVo.put("orderShipChannel", o.getShipChannel());
            }

            if (o.getOrderStatus().intValue() == 1) {
                orderVo.put("orderStatusText", "等待见面交易");
            }
            if (o.getOrderStatus().intValue() == 2) {
                orderVo.put("orderStatusText", "等待发货");
            }
            if (o.getOrderStatus().intValue() == 3) {
                orderVo.put("orderStatusText", "已发货");
            }
            if (o.getOrderStatus().intValue() == 4) {
                orderVo.put("orderStatusText", "租期中");
            }
            if (o.getOrderStatus().intValue() == 5) {
                orderVo.put("orderStatusText", "归还物品中");
            }
            if (o.getOrderStatus().intValue() == 6) {
                orderVo.put("orderStatusText", "交易成功");
            }

            if (o.getOrderStatus().intValue() == 7) {
                orderVo.put("orderStatusText", "订单取消");
            }

            UserVo postUserInfo = null;
            if (showType == 0) {
                postUserInfo = userService.findUserVoById(o.getPostUserId());
            } else {
                postUserInfo = userService.findUserVoById(o.getUserId());
            }
            orderVo.put("postUserInfo", postUserInfo);
            List<SrentOrderGoods> orderGoodsList = orderGoodsService.queryByOid(o.getId());
            //System.out.println(orderGoodsList);
            List<Map<String, Object>> orderGoodsVoList = new ArrayList<>(orderGoodsList.size());
            for (SrentOrderGoods orderGoods : orderGoodsList) {
                Map<String, Object> orderGoodsVo = new HashMap<>();
                orderGoodsVo.put("id", orderGoods.getId());
                orderGoodsVo.put("goodsName", orderGoods.getGoodsName());
//                System.out.println("商品名称：" + orderGoods.getGoodsName());
                orderGoodsVo.put("days", orderGoods.getDays());
                orderGoodsVo.put("picUrl", orderGoods.getPicUrl());
                //orderGoodsVo.put("postUserInfo", postUserInfo);
                orderGoodsVoList.add(orderGoodsVo);
            }


            orderVo.put("goodsList", orderGoodsVoList);
            orderVoList.add(orderVo);
        }

        return ResponseUtil.okList(orderVoList, orderList);
    }

    /**
     * 提交订单
     *
     * <p>
     * 1.创建订单表和订单商品信息表
     * 2.商品下架
     * </p>
     *
     * @param userId 买家ID
     * @param body   订单信息
     *               {
     *               postUserId: xxx,
     *               goodsId: xxx,
     *               days: xxx,
     *               addressId: xxx,
     *               goodsTotalPrice: xxx,
     *               freightPrice: xxx,
     *               orderTotalPrice: xxx,
     *               }
     * @return 提交订单操作结果
     */
    @Transactional
    public Object submit(Integer userId, String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        if (body == null) {
            return ResponseUtil.badArgumentValue();
        }
        Map<String, Object> data = null;

        Integer type = JacksonUtil.parseInteger(body, "type");
        //System.out.println("type为：" + type);

        // type 0: 见面交易 见面加1天 1: 快递交易(快递默认加3天快递时间)
        if (type == 0) {
            String address = JacksonUtil.parseString(body, "address");
            Integer goodsId = JacksonUtil.parseInteger(body, "goodsId");
            Short days = JacksonUtil.parseShort(body, "days");
            String phone = JacksonUtil.parseString(body, "phone");
            String time = JacksonUtil.parseString(body, "time");
            Integer postUserId = JacksonUtil.parseInteger(body, "postUserId");
            Float goodsTotalPriceF = new Float(JacksonUtil.parseString(body, "goodsTotalPrice"));
            Float freightPriceF = new Float(JacksonUtil.parseString(body, "freightPrice"));
            Float orderTotalPriceF = new Float(JacksonUtil.parseString(body, "orderTotalPrice"));
            BigDecimal goodsTotalPrice = new BigDecimal(Float.toString(goodsTotalPriceF));
            BigDecimal freightPrice = new BigDecimal(Float.toString(freightPriceF));
            BigDecimal orderTotalPrice = new BigDecimal(Float.toString(orderTotalPriceF));
            if (postUserId == null || goodsId == null) {
                return ResponseUtil.badArgument();
            }

            SrentUser user = userService.findById(userId);

            Integer orderId = null;
            SrentOrder order = null;

            // 订单
            order = new SrentOrder();
            order.setUserId(userId);
            order.setPostUserId(postUserId);
            order.setOrderSn(orderService.generateOrderSn(userId));
            if (type == 0) {
                Short status = new Short((short) 1);
                order.setOrderStatus(status);
            } else {
                Short status = new Short((short) 2);
                order.setOrderStatus(status);
            }

            order.setType(type);
            order.setConsignee(user.getNickname());
            order.setMobile(phone);
//            String detailedAddress = checkedAddress.getProvince() + checkedAddress.getCity()
//                    + checkedAddress.getCounty() + " " + checkedAddress.getAddressDetail();
            order.setAddress(address);
            order.setGoodsPrice(goodsTotalPrice);
            order.setFreightPrice(freightPrice);
            order.setOrderPrice(orderTotalPrice);

            if (type == 0) {
                //将交易事件放在快递公司中
                order.setShipChannel(time);
            }

            // 添加订单表项
            orderService.add(order);
            orderId = order.getId();

            // 向商品表添加订单号
            SrentGoods goods = goodsService.findById(goodsId);
            goods.setOrderId(orderId);
            goodsService.updateById(goods);

            SrentGoods srentGoods = goodsService.findById(goodsId);

            // 添加订单商品表项
            SrentOrderGoods orderGoods = new SrentOrderGoods();
            orderGoods.setOrderId(order.getId());
            orderGoods.setGoodsId(goodsId);
            orderGoods.setDeposit(srentGoods.getDeposit());
            orderGoods.setGoodsSn(srentGoods.getGoodsSn());
            orderGoods.setGoodsName(srentGoods.getName());
            orderGoods.setPicUrl((srentGoods.getGallery())[0]);
            orderGoods.setRentPrice(srentGoods.getRentPrice());

            // 增加三天时间
//            days = (short) (days + 1);
            orderGoods.setDays(days);
            orderGoods.setAddTime(LocalDateTime.now());

            orderGoodsService.add(orderGoods);

            // 商品设置为已租出
            goodsService.rentById(goodsId);

            data = new HashMap<>();
            data.put("orderId", orderId);
        } else {
            Integer postUserId = JacksonUtil.parseInteger(body, "postUserId");
            Integer goodsId = JacksonUtil.parseInteger(body, "goodsId");
            Short days = JacksonUtil.parseShort(body, "days");

            Integer addressId = JacksonUtil.parseInteger(body, "addressId");

            Float goodsTotalPriceF = new Float(JacksonUtil.parseString(body, "goodsTotalPrice"));
            Float freightPriceF = new Float(JacksonUtil.parseString(body, "freightPrice"));
            Float orderTotalPriceF = new Float(JacksonUtil.parseString(body, "orderTotalPrice"));
            BigDecimal goodsTotalPrice = new BigDecimal(Float.toString(goodsTotalPriceF));
            BigDecimal freightPrice = new BigDecimal(Float.toString(freightPriceF));
            BigDecimal orderTotalPrice = new BigDecimal(Float.toString(orderTotalPriceF));

            if (postUserId == null || goodsId == null || addressId == null) {
                return ResponseUtil.badArgument();
            }


            // 收获地址
            SrentAddress checkedAddress = addressService.query(userId, addressId);
            if (checkedAddress == null) {
                return ResponseUtil.badArgument();
            }

            Integer orderId = null;
            SrentOrder order = null;

            // 订单
            order = new SrentOrder();
            order.setUserId(userId);
            order.setPostUserId(postUserId);
            order.setOrderSn(orderService.generateOrderSn(userId));
            order.setType(type);
            Short status = new Short((short) 2);
            order.setOrderStatus(status);
            order.setConsignee(checkedAddress.getUsername());
            order.setMobile(checkedAddress.getTel());
            String detailedAddress = checkedAddress.getProvince() + checkedAddress.getCity()
                    + checkedAddress.getCounty() + " " + checkedAddress.getAddressDetail();
            order.setAddress(detailedAddress);
            order.setGoodsPrice(goodsTotalPrice);
            order.setFreightPrice(freightPrice);
            order.setOrderPrice(orderTotalPrice);

            // 添加订单表项
            orderService.add(order);
            orderId = order.getId();

            // 向商品表添加订单号
            SrentGoods goods = goodsService.findById(goodsId);
            goods.setOrderId(orderId);
            goodsService.updateById(goods);

            SrentGoods srentGoods = goodsService.findById(goodsId);

            // 添加订单商品表项
            SrentOrderGoods orderGoods = new SrentOrderGoods();
            orderGoods.setOrderId(order.getId());
            orderGoods.setGoodsId(goodsId);
            orderGoods.setDeposit(srentGoods.getDeposit());
            orderGoods.setGoodsSn(srentGoods.getGoodsSn());
            orderGoods.setGoodsName(srentGoods.getName());
            orderGoods.setPicUrl((srentGoods.getGallery())[0]);
            orderGoods.setRentPrice(srentGoods.getRentPrice());

            // 增加三天时间
            days = (short) (days + 2);
            orderGoods.setDays(days);
            orderGoods.setAddTime(LocalDateTime.now());

            orderGoodsService.add(orderGoods);

            // 商品设置为已租出
            goodsService.rentById(goodsId);

            data = new HashMap<>();
            data.put("orderId", orderId);
        }

        return ResponseUtil.ok(data);
    }

    /**
     * 订单详情
     *
     * @param userId  用户ID
     * @param orderId 订单ID
     * @return 订单详情
     */
    public Object detail(Integer userId, Integer orderId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        // 订单信息
        SrentOrder order = orderService.findById(orderId);
//        System.out.println(order);

        Map<String, Object> orderVo = new HashMap<String, Object>();
        orderVo.put("id", order.getId());
        orderVo.put("orderSn", order.getOrderSn());
        orderVo.put("addTime", order.getAddTime());
        orderVo.put("consignee", order.getConsignee());
        orderVo.put("mobile", order.getMobile());
        orderVo.put("address", order.getAddress());
        orderVo.put("goodsPrice", order.getGoodsPrice());
        orderVo.put("freightPrice", order.getFreightPrice());
        orderVo.put("orderPrice", order.getOrderPrice());
        orderVo.put("userId", order.getUserId());
        orderVo.put("postId", order.getPostUserId());
        orderVo.put("currentUserId", userId);
        orderVo.put("type", order.getType());
        if (order.getShipSn() == null && order.getShipChannel() == null) {
            orderVo.put("hasShip", false);
        } else {
            orderVo.put("hasShip", true);
            orderVo.put("orderShipSn", order.getShipSn());
            if (order.getType() == 0) {
                // 添加上当天时间
                String time = order.getAddTime().toLocalDate().toString();
                // System.out.println(time);
                String shipChannel = time + " " + order.getShipChannel();
//                System.out.println(shipChannel);
                orderVo.put("orderShipChannel", shipChannel);
            } else {
                orderVo.put("orderShipChannel", order.getShipChannel());
            }

        }
        if (order.getOrderStatus() == 1) {
            orderVo.put("orderStatusText", "等待见面交易");
        }
        if (order.getOrderStatus() == 2) {
            orderVo.put("orderStatusText", "等待发货");
        }
        if (order.getOrderStatus() == 3) {
            orderVo.put("orderStatusText", "已发货");
        }
        if (order.getOrderStatus() == 4) {
            orderVo.put("orderStatusText", "租期中");
        }
        if (order.getOrderStatus() == 5) {
            orderVo.put("orderStatusText", "归还物品中");
        }
        if (order.getOrderStatus() == 6) {
            orderVo.put("orderStatusText", "交易成功");
        }
        if (order.getOrderStatus() == 7) {
            orderVo.put("orderStatusText", "订单取消");
        }

        List<SrentOrderGoods> orderGoodsList = orderGoodsService.queryByOid(order.getId());
        // System.out.println(orderGoodsList);
        Map<String, Object> result = new HashMap<>();
        result.put("orderInfo", orderVo);
        result.put("orderGoods", orderGoodsList);
        return ResponseUtil.ok(result);
    }

    public Object ship(Integer userId, String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (body == null) {
            return ResponseUtil.badArgument();
        }
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        String shipSn = JacksonUtil.parseString(body, "expressNumber");
        String shipChannel = JacksonUtil.parseString(body, "expressCompany");
//        System.out.println(orderId);
//        System.out.println(shipSn);
//        System.out.println(shipChannel);

        SrentOrder order = orderService.findById(orderId);
        order.setShipSn(shipSn);
        order.setShipChannel(shipChannel);
        Short status = new Short((short) 3);
        order.setOrderStatus(status);
        orderService.update(order);
        return ResponseUtil.ok();
    }

    public Object cancel(Integer userId, String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        if (body == null) {
            return ResponseUtil.badArgument();
        }

        Integer orderId = JacksonUtil.parseInteger(body, "orderId");

        SrentOrder order = orderService.findById(orderId);
        List<Short> orderStatus = new ArrayList<Short>(2);
        order.setOrderStatus((short) 7);
        orderService.update(order);
        SrentOrderGoods orderGoods = orderGoodsService.queryByOidDays(orderId);
        System.out.println(orderGoods.getGoodsId());
        SrentGoods goods = goodsService.findById(orderGoods.getGoodsId());

        goods.setRented(false);
        goods.setOrderId(0);
        System.out.println(goods);
        if (goods == null) {
            return ResponseUtil.badArgument();
        }
        goodsService.updateById(goods);
        return ResponseUtil.ok();
    }

    public Object rent(Integer userId, String body) {
        if (userId == null) {
            ResponseUtil.unlogin();
        }

        if (body == null) {
            ResponseUtil.badArgument();
        }

        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        SrentOrder order = orderService.findById(orderId);
        List<Short> orderStatus = new ArrayList<Short>(2);
        order.setOrderStatus((short) 4);
        orderService.update(order);

        return ResponseUtil.ok();
    }

    public Object ritem(Integer userId, String body) {
        if (userId == null) {
            ResponseUtil.unlogin();
        }

        if (body == null) {
            ResponseUtil.badArgument();
        }

        Integer orderId = JacksonUtil.parseInteger(body, "orderId");
        SrentOrder order = orderService.findById(orderId);
        List<Short> orderStatus = new ArrayList<Short>(2);
        order.setOrderStatus((short) 5);
        orderService.update(order);

        return ResponseUtil.ok();
    }

}