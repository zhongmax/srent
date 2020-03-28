package com.csmaxwell.srent.db.service;

import com.csmaxwell.srent.db.dao.SrentSearchHistoryMapper;
import com.csmaxwell.srent.db.domain.SrentSearchHistory;
import com.csmaxwell.srent.db.domain.SrentSearchHistoryExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SrentSearchHistoryService {
    @Resource
    private SrentSearchHistoryMapper searchHistoryMapper;

    public void save(SrentSearchHistory searchHistory) {
        searchHistory.setAddTime(LocalDateTime.now());
        searchHistory.setUpdateTime(LocalDateTime.now());
        searchHistoryMapper.insertSelective(searchHistory);
    }

    public List<SrentSearchHistory> queryByUid(int uid) {
        SrentSearchHistoryExample example = new SrentSearchHistoryExample();
        example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        example.setDistinct(true);
        return searchHistoryMapper.selectByExampleSelective(example, SrentSearchHistory.Column.keyword);
    }

    public void deleteByUid(int uid) {
        SrentSearchHistoryExample example = new SrentSearchHistoryExample();
        example.or().andUserIdEqualTo(uid);
        searchHistoryMapper.logicalDeleteByExample(example);
    }

    public List<SrentSearchHistory> querySelective(String userId, String keyword, Integer page, Integer size, String sort, String order) {
        SrentSearchHistoryExample example = new SrentSearchHistoryExample();
        SrentSearchHistoryExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(userId)) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andKeywordLike("%" + keyword + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return searchHistoryMapper.selectByExample(example);
    }
}

