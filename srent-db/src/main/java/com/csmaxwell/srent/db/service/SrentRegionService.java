package com.csmaxwell.srent.db.service;

import com.csmaxwell.srent.db.dao.SrentRegionMapper;
import com.csmaxwell.srent.db.domain.SrentRegion;
import com.csmaxwell.srent.db.domain.SrentRegionExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SrentRegionService {

    @Resource
    private SrentRegionMapper regionMapper;

    public List<SrentRegion> getAll(){
        SrentRegionExample example = new SrentRegionExample();
        byte b = 4;
        example.or().andTypeNotEqualTo(b);
        return regionMapper.selectByExample(example);
    }

    public List<SrentRegion> queryByPid(Integer parentId) {
        SrentRegionExample example = new SrentRegionExample();
        example.or().andPidEqualTo(parentId);
        return regionMapper.selectByExample(example);
    }

    public SrentRegion findById(Integer id) {
        return regionMapper.selectByPrimaryKey(id);
    }

    public List<SrentRegion> querySelective(String name, Integer code, Integer page, Integer size, String sort, String order) {
        SrentRegionExample example = new SrentRegionExample();
        SrentRegionExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty(code)) {
            criteria.andCodeEqualTo(code);
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return regionMapper.selectByExample(example);
    }

}
