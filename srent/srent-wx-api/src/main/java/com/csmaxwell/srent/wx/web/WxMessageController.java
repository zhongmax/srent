package com.csmaxwell.srent.wx.web;

import com.csmaxwell.srent.core.util.JacksonUtil;
import com.csmaxwell.srent.core.util.ResponseUtil;
import com.csmaxwell.srent.db.domain.*;
import com.csmaxwell.srent.db.service.SrentGoodsService;
import com.csmaxwell.srent.db.service.SrentMessageService;
import com.csmaxwell.srent.db.service.SrentUserService;
import com.csmaxwell.srent.wx.annotation.LoginUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

@RestController
@RequestMapping("/wx/message")
@Validated
public class WxMessageController {
    private final Log logger = LogFactory.getLog(WxMessageController.class);

    @Autowired
    private SrentMessageService messageService;

    @Autowired
    private SrentUserService userService;

    @Autowired
    private SrentGoodsService goodsService;

    /**
     * 添加消息
     * @param userId
     * @param body
     * @return
     */
    @PostMapping("add")
    public Object add(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        Integer postUserId = JacksonUtil.parseInteger(body, "postUserId");
        Integer goodsId = JacksonUtil.parseInteger(body, "goodsId");
//        System.out.println(userId);
//        System.out.println(postUserId);
//        System.out.println(goodsId);

        // 查找数据库中是否有相同记录
        List<SrentMessage> messageList1 = messageService.findAllMessage();
        for (SrentMessage messageOne : messageList1) {
            if (messageOne.getUserId().equals(userId) &&
                messageOne.getPostUserId().equals(postUserId)
            )
                ResponseUtil.fail();
        }
        // 添加买家消息列表
        SrentMessage message = new SrentMessage();
        message.setUserId(userId);
        message.setPostUserId(postUserId);
        message.setGoodsId(goodsId);
        message.setGroupId(userId + "" +postUserId + "" + goodsId + "");
        List<SrentMessage> messageList = messageService.columnIsEquals(message);
        if (!(messageList.size() > 0)) {
            messageService.add(message);
        }

        // 添加卖家消息列表
        SrentMessage message1 = new SrentMessage();
        message1.setUserId(postUserId);
        message1.setPostUserId(userId);
        message1.setGoodsId(goodsId);
        message1.setGroupId(userId + "" +postUserId + "" + goodsId + "");

        List<SrentMessage> messageList2 = messageService.columnIsEquals(message1);
        if (!(messageList2.size() > 0)) {
            messageService.add(message1);
        }
//        System.out.println("增加一条字段");
        Map<String, Object> data = null;
        data = new HashMap<>();
        data.put("groupId", message.getGroupId());
        // System.out.println("groupId是：" + message.getGroupId());

        return ResponseUtil.ok(data);
    }

    /**
     * 消息列表
     * @param userId
     * @return
     */
    @GetMapping("list")
    public Object list(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        /**
         * 实现买家点击聊一聊时
         * 对方卖家聊天页面也会出现该条聊天信息
         *
         *
         */

//        List<SrentMessage> messageList1 = null;
//        List<SrentMessage> messageList2 = null;
//        List<SrentMessage> messageList3 = null;
//        messageList1 = messageService.queryByUserId(userId);
//        messageList2 = messageService.queryByPostUserId(userId);
//        System.out.println(messageList1);
//        for (SrentMessage list1: messageList1) {
//            for (SrentMessage list2: messageList2) {
//                if (list1.getUserId() == list2.getPostUserId()) {
//
//                }
//            }
//        }

        List<SrentMessage> messageList = messageService.queryByUserId(userId);
//        List<SrentMessage> postMessageList = messageService.queryByPostUserId(userId);
//        messageList.addAll(postMessageList);
        //System.out.println(messageList);
        List<Object> messages = new ArrayList<>(messageList.size());
        for (SrentMessage message : messageList) {
            Map<String, Object> m = new HashMap<>();
            m.put("id", message.getId());
            m.put("userId", message.getUserId());
            m.put("postUserId", message.getPostUserId());
//            System.out.println(message.getPostUserId());
            // 获取发布人信息
            UserVo userVoById = userService.findUserVoById(message.getPostUserId());
            m.put("postUserInfo", userVoById);

            // 获取商品图片
            SrentGoods byIdVO = goodsService.findByIdVO(message.getGoodsId());
            m.put("goodsInfo", byIdVO);

            m.put("goodsId", message.getGoodsId());
            m.put("groupId", message.getGroupId());
            // System.out.println(m);
            messages.add(m);
        }

        return ResponseUtil.okList(messages, messageList);
    }

//    public Object list(Integer userId, Integer showType, Integer page, Integer limit, String sort, String order) {
//        if (userId == null) {
//            return ResponseUtil.unlogin();
//        }
//
//        List<Short> orderStatus = new ArrayList<Short>(2);
//        List<SrentOrder> orderList = null;
//
//        if (showType == 0) {
//            orderStatus.add((short) 1);
//            orderList = orderService.queryByOrderStatus(userId, sort, order);
//        } else {
//            orderStatus.add((short) 1);
//            orderList = orderService.queryByOrderPostStatus(userId, sort, order);
//        }
//
//        //System.out.println(orderList);
//        System.out.println("orderList有几个" + orderList.size());
//        List<Map<String, Object>> orderVoList = new ArrayList<>(orderList.size());
//        for (SrentOrder o : orderList) {
//            Map<String, Object> orderVo = new HashMap<>();
//            orderVo.put("id", o.getId());
//
//            // 添加到期标志
//            LocalDate then = LocalDate.now();
//            boolean isExpiry = false;
//
//            SrentOrderGoods orderGoodsDate = orderGoodsService.queryByOidDays(o.getId());
//            System.out.println(orderGoodsDate.getDays());
//            System.out.println(orderGoodsDate.getAddTime());
//
//            LocalDate time = orderGoodsDate.getAddTime().toLocalDate();
//            Period p = Period.ofDays(orderGoodsDate.getDays());
//            then = time.plus(p);
////            System.out.println(then);
////            isExpiry = !(then.isAfter(LocalDate.now()));
//            isExpiry = !(then.isAfter(LocalDate.of(2019, 9, 13)));
//            System.out.println(isExpiry);
//
//            orderVo.put("isExpiry", isExpiry);
//            orderVo.put("date", then);
//
//            orderVo.put("orderSn", o.getOrderSn());
//            orderVo.put("orderPrice", o.getOrderPrice());
//            if (o.getShipSn() == null && o.getShipChannel() == null) {
//                orderVo.put("hasShip", false);
//            } else {
//                orderVo.put("hasShip", true);
//                orderVo.put("orderShipSn", o.getShipSn());
//                orderVo.put("orderShipChannel", o.getShipChannel());
//            }
//
//            if (o.getOrderStatus().intValue() == 1) {
//                orderVo.put("orderStatusText", "等待见面交易");
//            }
//            if (o.getOrderStatus().intValue() == 2) {
//                orderVo.put("orderStatusText", "等待发货");
//            }
//            if (o.getOrderStatus().intValue() == 3) {
//                orderVo.put("orderStatusText", "已发货");
//            }
//            if (o.getOrderStatus().intValue() == 4) {
//                orderVo.put("orderStatusText", "确认收货");
//            }
//            if (o.getOrderStatus().intValue() == 5) {
//                orderVo.put("orderStatusText", "归还物品");
//            }
//            if (o.getOrderStatus().intValue() == 6) {
//                orderVo.put("orderStatusText", "交易成功");
//            }
//
//            UserVo postUserInfo = null;
//            if (showType == 0) {
//                postUserInfo = userService.findUserVoById(o.getPostUserId());
//            } else {
//                postUserInfo = userService.findUserVoById(o.getUserId());
//            }
//            orderVo.put("postUserInfo", postUserInfo);
//            List<SrentOrderGoods> orderGoodsList = orderGoodsService.queryByOid(o.getId());
//            //System.out.println(orderGoodsList);
//            List<Map<String, Object>> orderGoodsVoList = new ArrayList<>(orderGoodsList.size());
//            for (SrentOrderGoods orderGoods : orderGoodsList) {
//                Map<String, Object> orderGoodsVo = new HashMap<>();
//                orderGoodsVo.put("id", orderGoods.getId());
//                orderGoodsVo.put("goodsName", orderGoods.getGoodsName());
//                System.out.println("商品名称：" + orderGoods.getGoodsName());
//
//                orderGoodsVo.put("picUrl", orderGoods.getPicUrl());
//                //orderGoodsVo.put("postUserInfo", postUserInfo);
//                orderGoodsVoList.add(orderGoodsVo);
//            }
//
//
//            orderVo.put("goodsList", orderGoodsVoList);
//            orderVoList.add(orderVo);
//        }
//
//        return ResponseUtil.okList(orderVoList, orderList);
//    }


    /**
     * 删除消息列表
     */
    @PostMapping("delete")
    public Object delete(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        Integer goodsId = JacksonUtil.parseInteger(body, "goodsId");
        SrentMessage message = messageService.queryByGoodsId(goodsId);
        if (message != null) {
            messageService.deleteById(message.getId());
        }

        return ResponseUtil.ok();
    }
}
