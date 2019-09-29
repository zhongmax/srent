package com.csmaxwell.srent.wx.service;

import com.csmaxwell.srent.db.domain.SrentUser;
import com.csmaxwell.srent.db.service.SrentUserService;
import com.csmaxwell.srent.wx.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserInfoService {

    @Autowired
    private SrentUserService userService;

    public UserInfo getInfo(Integer userId) {
        SrentUser user = userService.findById(userId);
        Assert.state(user != null, "用户不存在");

        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(user.getNickname());
        userInfo.setAvatarUrl(user.getAvatar());
        return userInfo;
    }
}
