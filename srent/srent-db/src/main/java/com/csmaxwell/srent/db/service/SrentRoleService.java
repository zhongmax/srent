package com.csmaxwell.srent.db.service;

import com.alibaba.druid.util.StringUtils;
import com.csmaxwell.srent.db.dao.SrentRoleMapper;
import com.csmaxwell.srent.db.domain.SrentRole;
import com.csmaxwell.srent.db.domain.SrentRoleExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by maxwell on 2019/8/23
 */
@Service
public class SrentRoleService {
    @Resource
    private SrentRoleMapper roleMapper;

    public Set<String> queryByIds(Integer[] roleIds) {
        Set<String> roles = new HashSet<String>();
        if(roleIds.length == 0){
            return roles;
        }

        SrentRoleExample example = new SrentRoleExample();
        example.or().andIdIn(Arrays.asList(roleIds)).andEnabledEqualTo(true).andDeletedEqualTo(false);
        List<SrentRole> roleList = roleMapper.selectByExample(example);

        for(SrentRole role : roleList){
            roles.add(role.getName());
        }

        return roles;

    }

    public List<SrentRole> querySelective(String name, Integer page, Integer limit, String sort, String order) {
        SrentRoleExample example = new SrentRoleExample();
        SrentRoleExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return roleMapper.selectByExample(example);
    }

    public SrentRole findById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    public void add(SrentRole role) {
        role.setAddTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        roleMapper.insertSelective(role);
    }

    public void deleteById(Integer id) {
        roleMapper.logicalDeleteByPrimaryKey(id);
    }

    public void updateById(SrentRole role) {
        role.setUpdateTime(LocalDateTime.now());
        roleMapper.updateByPrimaryKeySelective(role);
    }

    public boolean checkExist(String name) {
        SrentRoleExample example = new SrentRoleExample();
        example.or().andNameEqualTo(name).andDeletedEqualTo(false);
        return roleMapper.countByExample(example) != 0;
    }

    public List<SrentRole> queryAll() {
        SrentRoleExample example = new SrentRoleExample();
        example.or().andDeletedEqualTo(false);
        return roleMapper.selectByExample(example);
    }
}
