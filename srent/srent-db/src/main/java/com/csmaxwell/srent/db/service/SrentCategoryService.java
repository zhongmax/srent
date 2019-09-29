package com.csmaxwell.srent.db.service;

import com.csmaxwell.srent.db.dao.SrentCategoryMapper;
import com.csmaxwell.srent.db.domain.SrentCategory;
import com.csmaxwell.srent.db.domain.SrentCategory.*;
import com.csmaxwell.srent.db.domain.SrentCategoryExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SrentCategoryService {
    @Resource
    private SrentCategoryMapper categoryMapper;
    private SrentCategory.Column[] CHANNEL = {SrentCategory.Column.id, SrentCategory.Column.name, Column.iconUrl};

    public List<SrentCategory> queryL1WithoutRecommend(int offset, int limit) {
        SrentCategoryExample example = new SrentCategoryExample();
        example.or().andLevelEqualTo("L1").andNameNotEqualTo("推荐").andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return categoryMapper.selectByExample(example);
    }

    public List<SrentCategory> queryByAll() {
        SrentCategoryExample example = new SrentCategoryExample();
        example.or().andDeletedEqualTo(false);
        example.setOrderByClause("id asc");

        return categoryMapper.selectByExample(example);
    }

    public List<SrentCategory> queryL1(int offset, int limit) {
        SrentCategoryExample example = new SrentCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return categoryMapper.selectByExample(example);
    }

    public List<SrentCategory> queryL1() {
        SrentCategoryExample example = new SrentCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        return categoryMapper.selectByExample(example);
    }

    public List<SrentCategory> queryByPid(Integer pid) {
        SrentCategoryExample example = new SrentCategoryExample();
        example.or().andPidEqualTo(pid).andDeletedEqualTo(false);
        return categoryMapper.selectByExample(example);
    }

    public List<SrentCategory> queryL2ByIds(List<Integer> ids) {
        SrentCategoryExample example = new SrentCategoryExample();
        example.or().andIdIn(ids).andLevelEqualTo("L2").andDeletedEqualTo(false);
        return categoryMapper.selectByExample(example);
    }

    public SrentCategory findById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    public List<SrentCategory> querySelective(String id, String name, Integer page, Integer size, String sort, String order) {
        SrentCategoryExample example = new SrentCategoryExample();
        SrentCategoryExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(id)) {
            criteria.andIdEqualTo(Integer.valueOf(id));
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return categoryMapper.selectByExample(example);
    }

    public int updateById(SrentCategory category) {
        category.setUpdateTime(LocalDateTime.now());
        return categoryMapper.updateByPrimaryKeySelective(category);
    }

    public void deleteById(Integer id) {
        categoryMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(SrentCategory category) {
        category.setAddTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.insertSelective(category);
    }

    public List<SrentCategory> queryChannel() {
        SrentCategoryExample example = new SrentCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        return categoryMapper.selectByExampleSelective(example, CHANNEL);
    }
}
