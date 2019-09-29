package com.csmaxwell.srent.db.service;

import com.csmaxwell.srent.db.dao.OrderMapper;
import com.csmaxwell.srent.db.dao.SrentOrderMapper;
import com.csmaxwell.srent.db.domain.SrentOrder;
import com.csmaxwell.srent.db.domain.SrentOrderExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class SrentOrderService {
    @Resource
    private SrentOrderMapper srentOrderMapper;
    @Resource
    private OrderMapper orderMapper;

    public int add(SrentOrder order) {
        order.setAddTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        return srentOrderMapper.insertSelective(order);
    }

    public int update(SrentOrder order) {
        return srentOrderMapper.updateByPrimaryKeySelective(order);
    }

    public int count(Integer userId, Integer postUserId) {
        SrentOrderExample example = new SrentOrderExample();
        example.or().andUserIdEqualTo(userId).andPostUserIdEqualTo(postUserId).andDeletedEqualTo(false);
        return (int) srentOrderMapper.countByExample(example);
    }

    public SrentOrder findById(Integer orderId) {
        return srentOrderMapper.selectByPrimaryKey(orderId);
    }

    public LocalDate getOrderTime() {
        LocalDate now = LocalDate.now();
        return now;
    }

    private String getRandomNum(Integer num) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public int countByOrderSn(Integer userId, String orderSn) {
        SrentOrderExample example = new SrentOrderExample();
        example.or().andUserIdEqualTo(userId).andOrderSnEqualTo(orderSn).andDeletedEqualTo(false);
        return (int) srentOrderMapper.countByExample(example);
    }

    // TODO 根据时间戳产生订单号
    public String generateOrderSn(Integer userId) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        String now = df.format(LocalDate.now());
        String orderSn = now + getRandomNum(6);
        while (countByOrderSn(userId, orderSn) != 0) {
            orderSn = getRandomNum(6);
        }
        return orderSn;
    }

    public List<SrentOrder> queryByOrderStatus(Integer userId, List<Short> orderStatus, Integer page, Integer limit, String sort, String order) {
        SrentOrderExample example = new SrentOrderExample();
        example.setOrderByClause(SrentOrder.Column.addTime.desc());
        SrentOrderExample.Criteria criteria = example.or();
        criteria.andUserIdEqualTo(userId);
        if (orderStatus != null) {
            criteria.andOrderStatusIn(orderStatus);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);
        return srentOrderMapper.selectByExample(example);
    }

    public List<SrentOrder> queryByOrderStatus(Integer userId, String sort, String order) {
        SrentOrderExample example = new SrentOrderExample();
        example.setOrderByClause(SrentOrder.Column.addTime.desc());
        SrentOrderExample.Criteria criteria = example.or();
        criteria.andUserIdEqualTo(userId);
//        if (orderStatus != null) {
//            criteria.andOrderStatusIn(orderStatus);
//        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        // PageHelper.startPage(page, limit);
        return srentOrderMapper.selectByExample(example);
    }

    public List<SrentOrder> queryByOrderPostStatus(Integer userId, List<Short> orderStatus, Integer page, Integer limit, String sort, String order) {
        SrentOrderExample example = new SrentOrderExample();
        example.setOrderByClause(SrentOrder.Column.addTime.desc());
        SrentOrderExample.Criteria criteria = example.or();
        criteria.andPostUserIdEqualTo(userId);
        if (orderStatus != null) {
            criteria.andOrderStatusIn(orderStatus);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);
        return srentOrderMapper.selectByExample(example);
    }

    public List<SrentOrder> queryByOrderPostStatus(Integer userId, String sort, String order) {
        SrentOrderExample example = new SrentOrderExample();
        example.setOrderByClause(SrentOrder.Column.addTime.desc());
        SrentOrderExample.Criteria criteria = example.or();
        criteria.andPostUserIdEqualTo(userId);
//        if (orderStatus != null) {
//            criteria.andOrderStatusIn(orderStatus);
//        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        // PageHelper.startPage(page, limit);
        return srentOrderMapper.selectByExample(example);
    }




}
