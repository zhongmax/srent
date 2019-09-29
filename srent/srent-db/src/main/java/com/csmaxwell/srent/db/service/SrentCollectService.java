package com.csmaxwell.srent.db.service;

import com.csmaxwell.srent.db.dao.SrentCollectMapper;
import com.csmaxwell.srent.db.domain.SrentCollect;
import com.csmaxwell.srent.db.domain.SrentCollectExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SrentCollectService {
    @Resource
    private SrentCollectMapper collectMapper;

    public int count(int uid, Integer gid) {
        SrentCollectExample example = new SrentCollectExample();
        example.or().andUserIdEqualTo(uid).andValueIdEqualTo(gid).andDeletedEqualTo(false);
        return (int) collectMapper.countByExample(example);
    }

    public List<SrentCollect> queryByType(Integer userId, Byte type, Integer page, Integer limit, String sort, String order) {
        SrentCollectExample example = new SrentCollectExample();
        SrentCollectExample.Criteria criteria = example.createCriteria();

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        criteria.andUserIdEqualTo(userId);
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return collectMapper.selectByExample(example);
    }

    public List<SrentCollect> queryByType(Integer userId, Byte type, String sort, String order) {
        SrentCollectExample example = new SrentCollectExample();
        SrentCollectExample.Criteria criteria = example.createCriteria();

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        criteria.andUserIdEqualTo(userId);
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

//        PageHelper.startPage(page, limit);
        return collectMapper.selectByExample(example);
    }

    public int countByType(Integer userId, Byte type) {
        SrentCollectExample example = new SrentCollectExample();
        example.or().andUserIdEqualTo(userId).andTypeEqualTo(type).andDeletedEqualTo(false);
        return (int) collectMapper.countByExample(example);
    }

    public SrentCollect queryByTypeAndValue(Integer userId, Byte type, Integer valueId) {
        SrentCollectExample example = new SrentCollectExample();
        example.or().andUserIdEqualTo(userId).andValueIdEqualTo(valueId).andTypeEqualTo(type).andDeletedEqualTo(false);
        return collectMapper.selectOneByExample(example);
    }

    public void deleteById(Integer id) {
        collectMapper.logicalDeleteByPrimaryKey(id);
    }

    public int add(SrentCollect collect) {
        collect.setAddTime(LocalDateTime.now());
        collect.setUpdateTime(LocalDateTime.now());
        return collectMapper.insertSelective(collect);
    }

    public List<SrentCollect> querySelective(String userId, String valueId, Integer page, Integer size, String sort, String order) {
        SrentCollectExample example = new SrentCollectExample();
        SrentCollectExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(userId)) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (!StringUtils.isEmpty(valueId)) {
            criteria.andValueIdEqualTo(Integer.valueOf(valueId));
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return collectMapper.selectByExample(example);
    }
}
