package com.csmaxwell.srent.db.service;

import com.csmaxwell.srent.db.dao.SrentAddressMapper;
import com.csmaxwell.srent.db.domain.SrentAddress;
import com.csmaxwell.srent.db.domain.SrentAddressExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SrentAddressService {
    @Resource
    private SrentAddressMapper addressMapper;

    public List<SrentAddress> queryByUid(Integer uid) {
        SrentAddressExample example = new SrentAddressExample();
        example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        return addressMapper.selectByExample(example);
    }

    public SrentAddress query(Integer userId, Integer id) {
        SrentAddressExample example = new SrentAddressExample();
        example.or().andIdEqualTo(id).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return addressMapper.selectOneByExample(example);
    }

    public int add(SrentAddress address) {
        address.setAddTime(LocalDateTime.now());
        address.setUpdateTime(LocalDateTime.now());
        return addressMapper.insertSelective(address);
    }

    public int update(SrentAddress address) {
        address.setUpdateTime(LocalDateTime.now());
        return addressMapper.updateByPrimaryKeySelective(address);
    }

    public void delete(Integer id) {
        addressMapper.logicalDeleteByPrimaryKey(id);
    }

    public SrentAddress findDefault(Integer userId) {
        SrentAddressExample example = new SrentAddressExample();
        example.or().andUserIdEqualTo(userId).andIsDefaultEqualTo(true).andDeletedEqualTo(false);
        return addressMapper.selectOneByExample(example);
    }

    public void resetDefault(Integer userId) {
        SrentAddress address = new SrentAddress();
        address.setIsDefault(false);
        address.setUpdateTime(LocalDateTime.now());
        SrentAddressExample example = new SrentAddressExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        addressMapper.updateByExampleSelective(address, example);
    }

    public List<SrentAddress> querySelective(Integer userId, String name, Integer page, Integer limit, String sort, String order) {
        SrentAddressExample example = new SrentAddressExample();
        SrentAddressExample.Criteria criteria = example.createCriteria();

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andUsernameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return addressMapper.selectByExample(example);
    }
}
