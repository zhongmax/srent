package com.csmaxwell.srent.db.service;

import com.csmaxwell.srent.db.dao.SrentUserFormidMapper;
import com.csmaxwell.srent.db.domain.SrentUserFormid;
import com.csmaxwell.srent.db.domain.SrentUserFormidExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class SrentUserFormIdService {
    @Resource
    private SrentUserFormidMapper formidMapper;

    /**
     * 查找是否有可用的FormId
     *
     * @param openId
     * @return
     */
    public SrentUserFormid queryByOpenId(String openId) {
        SrentUserFormidExample example = new SrentUserFormidExample();
        //符合找到该用户记录，且可用次数大于1，且还未过期
        example.or().andOpenidEqualTo(openId).andExpireTimeGreaterThan(LocalDateTime.now());
        return formidMapper.selectOneByExample(example);
    }

    /**
     * 更新或删除FormId
     *
     * @param userFormid
     */
    public int updateUserFormId(SrentUserFormid userFormid) {
        //更新或者删除缓存
        if (userFormid.getIsprepay() && userFormid.getUseamount() > 1) {
            userFormid.setUseamount(userFormid.getUseamount() - 1);
            userFormid.setUpdateTime(LocalDateTime.now());
            return formidMapper.updateByPrimaryKey(userFormid);
        } else {
            return formidMapper.deleteByPrimaryKey(userFormid.getId());
        }
    }

    /**
     * 添加一个 FormId
     *
     * @param userFormid
     */
    public void addUserFormid(SrentUserFormid userFormid) {
        userFormid.setAddTime(LocalDateTime.now());
        userFormid.setUpdateTime(LocalDateTime.now());
        formidMapper.insertSelective(userFormid);
    }
}
