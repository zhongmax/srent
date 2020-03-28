// pages/goods/goods.js
var app = getApp();
var util = require('../../utils/util.js');
var api = require('../../config/api.js');
var user = require('../../utils/user.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    time: '',
    id: 0,
    goods: {},
    array: [],
    userHasCollect: 2,
    userInfo: {},
    postUserId: 0,
    collected: true,
    hasLogin: false,
    isExpiry: false,
    collectImage: '/static/images/collection.png',
    noCollectImage: '/static/images/collection.png',
    hasCollectImage: '/static/images/collection_selected.png',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    if (options.id) {
      this.setData({
        id: parseInt(options.id)
      });
      

    }
    //console.log(this.data.id)
  },

  // 收藏按钮点击事件
  onCollectionTap: function(event) {
    let that = this;
    util.request(api.CollectAddOrDelete, {
        type: 0,
        valueId: this.data.id
      }, "POST")
      .then(function(res) {
        if (that.data.userHasCollect == 1) {
          that.setData({
            collectImage: that.data.noCollectImage,
            userHasCollect: 0,
          });
        } else {
          that.setData({
            collectImage: that.data.hasCollectImage,
            userHasCollect: 1,
          });
        }
      });

  },

  onTalkTap: function(event) {
    var that = this;
    var goodsId = this.data.id;
    var postUserId = this.data.postUserId;
    var userId = this.data.userId;
    var groupId;
    util.request(api.MessageAdd, {
      postUserId: postUserId,
      goodsId: that.data.id,
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        that.setData({
          groupId: res.data.groupId
        })
        wx.navigateTo({
          // url: '/pages/chat/chat?id=' + id,
          url: '/pages/message/room/room?goodsId=' + goodsId + '&id=' + res.data.groupId + '&userId=' + userId,
        })
      }
    })
    
  },

  onShow: function() {
    var that = this;
    this.setData({
      hasLogin: app.globalData.hasLogin,
    })
    this.getGoodsInfo();
  },

  goBuy: function() {
    wx.setStorageSync("goodsInfo", this.data.goods);
    wx.setStorageSync("postUserId", this.data.postUserId);
    wx.setStorageSync("postUserInfo", this.data.userInfo);
    wx.navigateTo({
      url: '/pages/checkout/checkout',
    })
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function(ops) {
    if (ops.from === 'image') {
      console.log("点击图片");
    }
  },

  // 获取商品信息
  getGoodsInfo: function() {
    console.log("getGoodsInfo");
    let that = this;

    util.request(api.GoodsDetail, {
      id: that.data.id,
    }).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          isExpiry: res.data.isExpiry,
          userHasCollect: res.data.userHasCollect,
          goods: res.data.info,
          userInfo: res.data.userInfo,
          time: res.data.time,
          postUserId: res.data.postUserId,
          userHasCollect: res.data.userHasCollect,
          userId: res.data.userId,
        })
      }

      if (res.data.userHasCollect == 1) {
        that.setData({
          collectImage: that.data.hasCollectImage,
        });
      } else {
        that.setData({
          collectImage: that.data.noCollectImage
        });
      }

    });
  },

  goLogin: function() {
    wx.navigateTo({
      url: '/pages/auth/login/login',
    });
  },

  updateGoods() {
    wx.navigateTo({
      url: '/pages/updateGoods/updateGoods?id=' + this.data.id,
    })
  },

  deleteGoods() {
    var that = this;
    wx.showModal({
      title: '',
      content: '确认下架？',
      success: function (res) {
        if (res.confirm) {
          util.request(api.GoodsDelte, {
            id: that.data.id,
          }, 'POST').then(function (res) {
            if (res.errno === 0) {
              wx.showToast({
                title: '下架成功',
              });
              util.redirect('/pages/home/post/post')
            }
          })
        }
      }
    })
  }


})