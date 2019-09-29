// pages/index/index.js
const util = require('../../utils/util.js');
const api = require('../../config/api.js');
const app = getApp();

// var sortData = require('/../..//data/sort_data.js')
// var itemData = require('/../..//data/new_item.js')

Page({
  /**
   * 页面的初始数据
   */
  data: {
    openid: "",
    item_list: [],
    channel: [],
    swiperCurrent: 0,
    swiperList: [
      { id: 0, img: "http://pxc4naypd.bkt.clouddn.com/banner1_1.jpg"},
      { id: 1, img: "http://pxc4naypd.bkt.clouddn.com/banner2_1.jpg"},
      { id: 2, img: "http://pxc4naypd.bkt.clouddn.com/banner3_1.jpg"},
    ],
    newGoods: [],
    goodsCount: 0,
    current: 0,
  },

  // swiper点击
  swiperTap: function(e) {
    
    var postId = e.target.dataset.postid;
    console.log(postId);
    wx.navigateTo({
      url: '../activity/activity?id=' + postId,
      success: function (res) { },
      fail: function (res) { },
      complete: function (res) { },
    })
  },

  // 最新商品点击
  onItemTap: function(event) {
    var id = event.currentTarget.dataset.itemid;
    // console.log(itemId);
    wx.navigateTo({
      url: '../goods/goods?id=' + id,
    })
  },

  getIndexData: function () {
    let that = this;
    util.request(api.IndexUrl).then(function (res) {
      if (res.errno === 0) {
        that.setData({
          newGoods: res.data.allGoodsList,
          channel: res.data.channel,
        });
      }
    });
    // util.request(api.GoodsCount).then(function (res) {
    //   that.setData({
    //     goodsCount: res.data
    //   })
    // })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
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
    //获取用户的登录信息
    if (app.globalData.hasLogin) {
      let userInfo = wx.getStorageSync('userInfo');
      this.setData({
        userInfo: userInfo,
        hasLogin: true
      });
    };

    this.getIndexData();
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

  onSearchTap: function(event) {
    wx.navigateTo({
      url: '/pages/search/search',
    })
  }
})