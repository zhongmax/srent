package com.csmaxwell.srent.db.service;

import com.csmaxwell.srent.db.dao.SrentOrderGoodsMapper;
import com.csmaxwell.srent.db.domain.SrentOrderGoods;
import com.csmaxwell.srent.db.domain.SrentOrderGoodsExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SrentOrderGoodsService {
    @Resource
    private SrentOrderGoodsMapper orderGoodsMapper;

    public int add(SrentOrderGoods orderGoods) {
        orderGoods.setAddTime(LocalDateTime.now());
        orderGoods.setUpdateTime(LocalDateTime.now());
        return orderGoodsMapper.insertSelective(orderGoods);
    }

    public List<SrentOrderGoods> queryByOid(Integer orderId) {
        SrentOrderGoodsExample example = new SrentOrderGoodsExample();
        example.or().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
        return orderGoodsMapper.selectByExample(example);
    }

    public SrentOrderGoods queryByOidDays(Integer orderId) {
        SrentOrderGoodsExample example = new SrentOrderGoodsExample();
        example.or().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
        return orderGoodsMapper.selectOneByExample(example);
    }



}
