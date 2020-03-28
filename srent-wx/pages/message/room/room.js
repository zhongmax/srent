const app = getApp()
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../utils/user.js');
Page({
  data: {
    id: 0,
    avatarUrl: './user-unlogin.png',
    userInfo: null,
    logged: false,
    takeSession: false,
    requestResult: '',
    // chatRoomEnvId: 'release-f8415a',
    chatRoomCollection: 'chatroom',
    chatRoomGroupId: 'demo2',
    chatRoomGroupName: '聊天室',

    // functions for used in chatroom components
    onGetUserInfo: null,
    getOpenID: null,

    // 自定义
    room: '',
    userId: 0,
  },

  onLoad: function(options) {
    if (options.id && options.goodsId && options.userId) {
      this.setData({
        id: parseInt(options.goodsId),
        room: options.id,
        userId: options.userId,
      })
    };

    this.getBothUserInfo();
    this.onGetUserInfo();

    // // 获取用户信息
    // wx.getSetting({
    //   success: res => {
    //     if (res.authSetting['scope.userInfo']) {
    //       // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
    //       wx.getUserInfo({
    //         success: res => {
    //           this.setData({
    //             avatarUrl: res.userInfo.avatarUrl,
    //             userInfo: res.userInfo
    //           })
    //         }
    //       })
    //     }
    //   }
    // })

    this.setData({
      onGetUserInfo: this.onGetUserInfo,
      getOpenID: this.getOpenID,
    })

    wx.getSystemInfo({
      success: res => {
        console.log('system info', res)
        if (res.safeArea) {
          const {
            top,
            bottom
          } = res.safeArea
          this.setData({
            // containerStyle: `padding-top: ${(/ios/i.test(res.system) ? 10 : 20) + top}px; padding-bottom: ${20 + res.windowHeight - bottom}px`,
          })
        }
      },
    })
  },

  getBothUserInfo: function(id) {
    var that = this;
    //console.log(id);
    //console.log("生命周期");
    util.request(api.GoodsDetail, {
      id: that.data.id
    }).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          goods: res.data.info,
          postUserInfo: res.data.userInfo,
          postUserId: res.data.postUserId,
        });
        if (res.data.userInfo.nickName !== that.data.userInfo.nickName) {
          wx.setNavigationBarTitle({
            title: that.data.postUserInfo.nickName,
          })
        }
      }
    });

    var postUserId = wx.getStorageSync('postUserId');
    // console.log(postUserId);
    // util.request(api.MessageAdd, {
    //   postUserId: postUserId,
    //   goodsId: that.data.id,
    // }, 'POST').then(function (res) {
    //   if (res.errno === 0) {
    //     console.log("发送成功");
    //   }
    // })

  },

  goBuy: function() {
    wx.setStorageSync("goodsInfo", this.data.goods);

    wx.setStorageSync("postUserInfo", this.data.userInfo);
    wx.navigateTo({
      url: '/pages/checkout/checkout',
    })
  },

  getOpenID: async function() {
    // console.log("openId是", wx.getStorageSync('openId'));
    return wx.getStorageSync('openId');
  },

  onGetUserInfo: function() {
    var userInfo = wx.getStorageSync('userInfo');
    this.setData({
      userInfo: userInfo,
    });
  },

  onShareAppMessage() {
    return {
      title: '即时通信 Demo',
      path: '/pages/im/room/room',
    }
  },
})