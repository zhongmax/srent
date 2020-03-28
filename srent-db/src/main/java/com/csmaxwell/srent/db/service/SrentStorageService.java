package com.csmaxwell.srent.db.service;

import com.csmaxwell.srent.db.dao.SrentStorageMapper;
import com.csmaxwell.srent.db.domain.SrentStorage;
import com.csmaxwell.srent.db.domain.SrentStorageExample;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SrentStorageService {
    @Autowired
    private SrentStorageMapper storageMapper;

    public void deleteByKey(String key) {
        SrentStorageExample example = new SrentStorageExample();
        example.or().andKeyEqualTo(key);
        storageMapper.logicalDeleteByExample(example);
    }

    public void add(SrentStorage storageInfo) {
        storageInfo.setAddTime(LocalDateTime.now());
        storageInfo.setUpdateTime(LocalDateTime.now());
        storageMapper.insertSelective(storageInfo);
    }

    public SrentStorage findByKey(String key) {
        SrentStorageExample example = new SrentStorageExample();
        example.or().andKeyEqualTo(key).andDeletedEqualTo(false);
        return storageMapper.selectOneByExample(example);
    }

    public int update(SrentStorage storageInfo) {
        storageInfo.setUpdateTime(LocalDateTime.now());
        return storageMapper.updateByPrimaryKeySelective(storageInfo);
    }

    public SrentStorage findById(Integer id) {
        return storageMapper.selectByPrimaryKey(id);
    }

    public List<SrentStorage> querySelective(String key, String name, Integer page, Integer limit, String sort, String order) {
        SrentStorageExample example = new SrentStorageExample();
        SrentStorageExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(key)) {
            criteria.andKeyEqualTo(key);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return storageMapper.selectByExample(example);
    }
}

