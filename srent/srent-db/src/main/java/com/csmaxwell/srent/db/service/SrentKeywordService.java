package com.csmaxwell.srent.db.service;

import com.csmaxwell.srent.db.dao.SrentKeywordMapper;
import com.csmaxwell.srent.db.domain.SrentKeyword;
import com.csmaxwell.srent.db.domain.SrentKeywordExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SrentKeywordService {
    @Resource
    private SrentKeywordMapper keywordsMapper;

    public SrentKeyword queryDefault() {
        SrentKeywordExample example = new SrentKeywordExample();
        example.or().andIsDefaultEqualTo(true).andDeletedEqualTo(false);
        return keywordsMapper.selectOneByExample(example);
    }

    public List<SrentKeyword> queryHots() {
        SrentKeywordExample example = new SrentKeywordExample();
        example.or().andIsHotEqualTo(true).andDeletedEqualTo(false);
        return keywordsMapper.selectByExample(example);
    }

    public List<SrentKeyword> queryByKeyword(String keyword, Integer page, Integer limit) {
        SrentKeywordExample example = new SrentKeywordExample();
        example.setDistinct(true);
        example.or().andKeywordLike("%" + keyword + "%").andDeletedEqualTo(false);
        PageHelper.startPage(page, limit);
        return keywordsMapper.selectByExampleSelective(example, SrentKeyword.Column.keyword);
    }

    public List<SrentKeyword> querySelective(String keyword, String url, Integer page, Integer limit, String sort, String order) {
        SrentKeywordExample example = new SrentKeywordExample();
        SrentKeywordExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(keyword)) {
            criteria.andKeywordLike("%" + keyword + "%");
        }
        if (!StringUtils.isEmpty(url)) {
            criteria.andUrlLike("%" + url + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return keywordsMapper.selectByExample(example);
    }

    public void add(SrentKeyword keywords) {
        keywords.setAddTime(LocalDateTime.now());
        keywords.setUpdateTime(LocalDateTime.now());
        keywordsMapper.insertSelective(keywords);
    }

    public SrentKeyword findById(Integer id) {
        return keywordsMapper.selectByPrimaryKey(id);
    }

    public int updateById(SrentKeyword keywords) {
        keywords.setUpdateTime(LocalDateTime.now());
        return keywordsMapper.updateByPrimaryKeySelective(keywords);
    }

    public void deleteById(Integer id) {
        keywordsMapper.logicalDeleteByPrimaryKey(id);
    }
}

