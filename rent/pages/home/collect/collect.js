// pages/home/collect/collect.js
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');

var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    collectList: [],
    type: 0,
    page: 1,
    limit: 10,
    totalPages: 1,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getCollectList();
  },

  getCollectList() {
    wx.showLoading({
      title: '加载中...',
    });
    let that = this;
    util.request(api.CollectList, {
      type: that.data.type,
      page: that.data.page,
      limit: that.data.limit
    }).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          collectList: that.data.collectList.concat(res.data.list),
          totalPages: res.data.pages
        });
      }
      wx.hideLoading();
    });
  },

  // 按下事件开始
  touchStart: function(e) {
    let that = this;
    that.setData({
      touchStart: e.timeStamp,
    })
  },

  touchEnd: function(e) {
    let that = this;
    that.setData({
      touchEnd: e.timeStamp
    })
  },

  openGoods(event) {
    let that = this;
    let index = event.currentTarget.dataset.index;
    let goodsId = this.data.collectList[index].valueId;

    // 触摸时间距离页面打开的毫秒数
    var touchTime = that.data.touchEnd - that.data.touchStart;
    // 如果按下事件超过350ms
    if(touchTime > 350) {
      wx.showModal({
        title: '',
        content: '确定删除该商品吗？',
        success: function(res) {
          if (res.confirm) {
            util.request(api.CollectAddOrDelete, {
              type: that.data.type,
              valueId: goodsId
            }, 'POST').then(function(res) {
              if (res.errno === 0) {
                wx.showToast({
                  title: '删除成功',
                  icon: 'success',
                  duration: 2000
                });
                that.data.collectList.splice(index, 1);
                that.setData({
                  collectList: that.data.collectList
                });
              }
            })
          }
        }
      })
    } else {
      wx.navigateTo({
        url: '/pages/goods/goods?id=' + goodsId,
      })
    }
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

  }
})