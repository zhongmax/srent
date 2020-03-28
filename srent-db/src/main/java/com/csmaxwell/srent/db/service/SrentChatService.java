package com.csmaxwell.srent.db.service;

import com.csmaxwell.srent.db.dao.SrentChatMapper;
import com.csmaxwell.srent.db.domain.SrentChat;
import com.csmaxwell.srent.db.domain.SrentChatExample;
import com.csmaxwell.srent.db.domain.SrentUserExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SrentChatService {
    @Resource
    private SrentChatMapper chatMapper;

    public List<SrentChat> findChatContent(Integer userId, Integer postUserId, Integer goodsId) {
        SrentChatExample example = new SrentChatExample();
        SrentChatExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andPostUserIdEqualTo(postUserId);
        criteria.andGoodsIdEqualTo(goodsId);
        criteria.andDeletedEqualTo(false);

        return chatMapper.selectByExample(example);
    }

    public int add(SrentChat chat) {
        chat.setAddTime(LocalDateTime.now());
        chat.setUpdateTime(LocalDateTime.now());
        return chatMapper.insertSelective(chat);
    }
}
