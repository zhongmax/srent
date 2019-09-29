// pages/home/home.js
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../utils/user.js');

var app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {},
    img: '/static/images/user-unlogin.png',
    nickName: '点击登录',
    avatarUrl: '',
    hasLogin: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
  },

  // 登录
  goLogin() {
    wx.navigateTo({
      url: '/pages/auth/login/login',
    });
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    // 获取用户登录信息
    if (app.globalData.hasLogin) {
      var userInfo = wx.getStorageSync('userInfo');
      this.setData({
        userInfo: userInfo,
        hasLogin: true,
        avatarUrl: userInfo.avatarUrl,
        nickName: userInfo.nickName,
      })
    }
  },

  exitLogin: function () {
    wx.showModal({
      title: '',
      confirmColor: '#b4282d',
      content: '退出登录？',
      success: function (res) {
        if (!res.confirm) {
          return;
        }

        util.request(api.AuthLogout, {}, 'POST');
        app.globalData.hasLogin = false;
        wx.removeStorageSync('openId');
        wx.removeStorageSync('token');
        wx.removeStorageSync('userInfo');
        wx.removeStorageSync('goodsInfo');
        wx.removeStorageSync('postUserId');
        wx.removeStorageSync('postUserInfo');
        wx.reLaunch({
          url: '/pages/index/index'
        });
      }
    })

  },

  moreUserInfo: function() {
    wx.navigateTo({
      url: '/pages/home/moreSettings/moreSettings',
    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  onMyReleaseTap: function (event) {
    wx.navigateTo({
      url: '/pages/home/post/post',
    })
  },

  onMyRentTap: function (event) {
    wx.navigateTo({
      url: '/pages/home/rent/rent',
    })
  },

  jumpPage: function () {
    wx.navigateTo({
      url: '/pages/home/pc/pc',
    })
  },
  onCollectionTap: function () {
    wx.navigateTo({
      url: '/pages/home/collect/collect',
    })
  },
  onRentedTap: function () {
    wx.navigateTo({
      url: '/pages/home/rented/rented',
    })
  },
  onReparationTap: function() {
    wx.navigateTo({
      url: '/pages/home/reparation/reparation',
    })
  },
})