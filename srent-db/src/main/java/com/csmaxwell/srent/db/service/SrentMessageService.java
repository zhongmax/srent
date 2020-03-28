package com.csmaxwell.srent.db.service;

import com.csmaxwell.srent.db.dao.SrentMessageMapper;
import com.csmaxwell.srent.db.domain.SrentMessage;
import com.csmaxwell.srent.db.domain.SrentMessageExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SrentMessageService {
    @Resource
    private SrentMessageMapper messageMapper;

    public int add(SrentMessage message) {
        message.setAddTime(LocalDateTime.now());
        message.setUpdateTime(LocalDateTime.now());
        return messageMapper.insertSelective(message);
    }

    public List<SrentMessage> columnIsEquals(SrentMessage message) {
        SrentMessageExample example = new SrentMessageExample();
        example.or().andUserIdEqualTo(message.getUserId()).andPostUserIdEqualTo(message.getPostUserId()).andGoodsIdEqualTo(message.getGoodsId())
        .andDeletedEqualTo(false);
        return messageMapper.selectByExample(example);
    }

    public List<SrentMessage> queryByUserId(Integer userId) {
        SrentMessageExample example = new SrentMessageExample();
        SrentMessageExample.Criteria criteria = example.createCriteria();

        criteria.andUserIdEqualTo(userId);
        criteria.andDeletedEqualTo(false);

        return messageMapper.selectByExample(example);
    }

    public List<SrentMessage> queryByPostUserId(Integer userId) {
        SrentMessageExample example = new SrentMessageExample();
        SrentMessageExample.Criteria criteria = example.createCriteria();

        criteria.andPostUserIdEqualTo(userId).andUserIdNotEqualTo(userId);
        criteria.andDeletedEqualTo(false);

        return messageMapper.selectByExample(example);
    }

    public List<SrentMessage> findAllMessage() {
        SrentMessageExample example = new SrentMessageExample();
        SrentMessageExample.Criteria criteria = example.createCriteria();

        criteria.andDeletedEqualTo(false);

        return messageMapper.selectByExample(example);
    }

    public SrentMessage queryByGoodsId(Integer goodsId) {
        SrentMessageExample example = new SrentMessageExample();
        example.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return messageMapper.selectOneByExample(example);
    }

    public void deleteById(Integer id) {
        messageMapper.logicalDeleteByPrimaryKey(id);
    }
}
