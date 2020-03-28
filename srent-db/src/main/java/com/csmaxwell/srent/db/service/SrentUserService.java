package com.csmaxwell.srent.db.service;

import com.csmaxwell.srent.db.dao.SrentUserMapper;
import com.csmaxwell.srent.db.domain.SrentUser;
import com.csmaxwell.srent.db.domain.SrentUserExample;
import com.csmaxwell.srent.db.domain.UserVo;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SrentUserService {
    @Resource
    private SrentUserMapper userMapper;

    public SrentUser findById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public UserVo findUserVoById(Integer userId) {
        SrentUser user = findById(userId);
        UserVo userVo = new UserVo();
        userVo.setNickName(user.getNickname());
        userVo.setAvatarUrl(user.getAvatar());
        return userVo;
    }

    public SrentUser queryByOid(String openId) {
        SrentUserExample example = new SrentUserExample();
        example.or().andWeixinOpenidEqualTo(openId).andDeletedEqualTo(false);
        return userMapper.selectOneByExample(example);
    }

    public void add(SrentUser user) {
        user.setAddTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insertSelective(user);
    }

    public int updateById(SrentUser user) {
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.updateByPrimaryKeySelective(user);
    }

    public List<SrentUser> querySelective(String username, String mobile, Integer page, Integer size, String sort, String order) {
        SrentUserExample example = new SrentUserExample();
        SrentUserExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(username)) {
            criteria.andUsernameLike("%" + username + "%");
        }
        if (!StringUtils.isEmpty(mobile)) {
            criteria.andMobileEqualTo(mobile);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return userMapper.selectByExample(example);
    }

    public int count() {
        SrentUserExample example = new SrentUserExample();
        example.or().andDeletedEqualTo(false);

        return (int) userMapper.countByExample(example);
    }

    public List<SrentUser> queryByUsername(String username) {
        SrentUserExample example = new SrentUserExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return userMapper.selectByExample(example);
    }

    public boolean checkByUsername(String username) {
        SrentUserExample example = new SrentUserExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return userMapper.countByExample(example) != 0;
    }

    public List<SrentUser> queryByMobile(String mobile) {
        SrentUserExample example = new SrentUserExample();
        example.or().andMobileEqualTo(mobile).andDeletedEqualTo(false);
        return userMapper.selectByExample(example);
    }

    public List<SrentUser> queryByOpenid(String openid) {
        SrentUserExample example = new SrentUserExample();
        example.or().andWeixinOpenidEqualTo(openid).andDeletedEqualTo(false);
        return userMapper.selectByExample(example);
    }

    public void deleteById(Integer id) {
        userMapper.logicalDeleteByPrimaryKey(id);
    }

//    public List<SrentUser> querySelective(String username, String mobile, Integer page, Integer size, String sort, String order) {
//        SrentUserExample example = new SrentUserExample();
//        SrentUserExample.Criteria criteria = example.createCriteria();
//
//        if (!StringUtils.isEmpty(username)) {
//            criteria.andUsernameLike("%" + username + "%");
//        }
//        if (!StringUtils.isEmpty(mobile)) {
//            criteria.andMobileEqualTo(mobile);
//        }
//        criteria.andDeletedEqualTo(false);
//
//        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
//            example.setOrderByClause(sort + " " + order);
//        }
//
//        PageHelper.startPage(page, size);
//        return userMapper.selectByExample(example);
//    }
}
