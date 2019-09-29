package com.csmaxwell.srent.db.service;

import com.csmaxwell.srent.db.dao.SrentFootprintMapper;
import com.csmaxwell.srent.db.domain.SrentFootprint;
import com.csmaxwell.srent.db.domain.SrentFootprintExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SrentFootprintService {
    @Resource
    private SrentFootprintMapper footprintMapper;

    public List<SrentFootprint> queryByAddTime(Integer userId, Integer page, Integer size) {
        SrentFootprintExample example = new SrentFootprintExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        example.setOrderByClause(SrentFootprint.Column.addTime.desc());
        PageHelper.startPage(page, size);
        return footprintMapper.selectByExample(example);
    }

    public SrentFootprint findById(Integer id) {
        return footprintMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        footprintMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(SrentFootprint footprint) {
        footprint.setAddTime(LocalDateTime.now());
        footprint.setUpdateTime(LocalDateTime.now());
        footprintMapper.insertSelective(footprint);
    }

    public List<SrentFootprint> querySelective(String userId, String goodsId, Integer page, Integer size, String sort, String order) {
        SrentFootprintExample example = new SrentFootprintExample();
        SrentFootprintExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(userId)) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (!StringUtils.isEmpty(goodsId)) {
            criteria.andGoodsIdEqualTo(Integer.valueOf(goodsId));
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return footprintMapper.selectByExample(example);
    }
}

