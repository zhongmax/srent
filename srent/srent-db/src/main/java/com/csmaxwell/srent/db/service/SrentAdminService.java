package com.csmaxwell.srent.db.service;

import com.csmaxwell.srent.db.dao.SrentAdminMapper;
import com.csmaxwell.srent.db.domain.SrentAdmin;
import com.csmaxwell.srent.db.domain.SrentAdmin.*;
import com.csmaxwell.srent.db.domain.SrentAdminExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SrentAdminService {
    private final Column[] result = new Column[]{Column.id, Column.username, Column.avatar};
    @Resource
    private SrentAdminMapper adminMapper;

    public List<SrentAdmin> findAdmin(String username) {
        SrentAdminExample example = new SrentAdminExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return adminMapper.selectByExample(example);
    }

    public SrentAdmin findAdmin(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    public List<SrentAdmin> querySelective(String username, Integer page, Integer limit, String sort, String order) {
        SrentAdminExample example = new SrentAdminExample();
        SrentAdminExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(username)) {
            criteria.andUsernameLike("%" + username + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return adminMapper.selectByExampleSelective(example, result);
    }

    public int updateById(SrentAdmin admin) {
        admin.setUpdateTime(LocalDateTime.now());
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    public void deleteById(Integer id) {
        adminMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(SrentAdmin admin) {
        admin.setAddTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());
        adminMapper.insertSelective(admin);
    }

    public SrentAdmin findById(Integer id) {
        return adminMapper.selectByPrimaryKeySelective(id, result);
    }

    public List<SrentAdmin> all() {
        SrentAdminExample example = new SrentAdminExample();
        example.or().andDeletedEqualTo(false);
        return adminMapper.selectByExample(example);
    }
}

