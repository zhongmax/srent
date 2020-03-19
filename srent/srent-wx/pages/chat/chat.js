// pages/chat/chat.js
var util = require('../../utils/util.js');
var api = require('../../config/api.js');
var user = require('../../utils/user.js');

var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    avatar: "",
    nickName: "",
    id: 0,
    picUrl: "",
    postUserId: 0,
    userInfo: {},
    postUserInfo: {},
    hasLogin: false,
    goods: {},
    chatContent: "",
    newsList: [],
    files: [],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    if (options.id) {
      this.setData({
        id: parseInt(options.id),
        hasLogin: app.globalData.hasLogin,
      })
      
      //console.log(parseInt(options.postId));
      this.getBothUserInfo(parseInt(options.id));
    }

  },

  getBothUserInfo: function(id) {
    var that = this;
    //console.log(id);
    //console.log("生命周期");
    util.request(api.GoodsDetail, {
      id: that.data.id
    }).then(function (res) {
      if (res.errno === 0) {
        that.setData({
          goods: res.data.info,
          postUserInfo: res.data.userInfo,
          postUserId: res.data.postUserId,
        });
        
        wx.setNavigationBarTitle({
          title: that.data.postUserInfo.nickName,
        })
      }
    });

    var userInfo = wx.getStorageSync('userInfo');
    this.setData({
      userInfo: userInfo,
    });

    

  },

  chatInput: function(event) {
    let value = event.detail.value;
    this.setData({
      chatContent: value,
    })
  },

  goBuy: function () {
    wx.setStorageSync("goodsInfo", this.data.goods);
    
    wx.setStorageSync("postUserInfo", this.data.userInfo);
    wx.navigateTo({
      url: '/pages/checkout/checkout',
    })
  },

  chatDone: function() {
    var that = this;
    if (this.data.chatContent) {
      util.request(api.ChatAdd, {
        postUserId: that.data.postUserId,
        goodsId: that.data.id,
        content: that.data.chatContent,
      }, 'POST').then(function(res) {
        if (res.errno === 0) {
          console.log("发送消息成功");
        }
      });
      var list = [];
      list = this.data.newsList;
      var tmp = {
        'message': this.data.chatContent,
        'date': util.formatTime(new Date()),
        type: 0
      };
      console.log(tmp);
      list.push(tmp);
      this.setData({
        newsList: list,
        chatContent: null,
      })
    }
  },

  sendPicture: function (e) {
    var that = this;
    wx.chooseImage({
      count: 1,
      sizeType: ['original', 'compressed'],
      sourceType: ['album', 'camera'],
      success: function (res) {
        const files = that.data.files.concat(res.tempFilePaths);
        console.log(files);
        var list = [];
        list = that.data.newsList;
        var tmp = {
          'message': files,
          'date': util.formatTime(new Date()),
          type: 1
        };
        list.push(tmp);
        that.setData({
          newsList: list,
          chatContent: null,
          files: files,
        })
      }
    })
  },

  previewImage: function (e) {
    console.log("点击");
    var url = e.currentTarget.id;
    wx.previewImage({
      current: this.data.newsList[e.currentTarget.dataset.id].message[e.currentTarget.dataset.id],
      urls: this.data.files, // 需要预览的图片http链接列表
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})