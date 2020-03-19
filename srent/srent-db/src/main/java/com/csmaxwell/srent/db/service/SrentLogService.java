package com.csmaxwell.srent.db.service;

import com.csmaxwell.srent.db.dao.SrentLogMapper;
import com.csmaxwell.srent.db.domain.SrentLog;
import com.csmaxwell.srent.db.domain.SrentLogExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by maxwell on 2019/8/23
 */
@Service
public class SrentLogService {
    @Resource
    private SrentLogMapper logMapper;

    public void deleteById(Integer id) {
        logMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(SrentLog log) {
        log.setAddTime(LocalDateTime.now());
        log.setUpdateTime(LocalDateTime.now());
        logMapper.insertSelective(log);
    }

    public List<SrentLog> querySelective(String name, Integer page, Integer size, String sort, String order) {
        SrentLogExample example = new SrentLogExample();
        SrentLogExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andAdminLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return logMapper.selectByExample(example);
    }
}

