// pages/home/post/post.js
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    goodsList: [],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

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
    this.getGoodsList();
  },

  getGoodsList() {
    let that = this;
    util.request(api.OrderPost, {

    }).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          goodsList: res.data.goodsList,
        })
      }
    })
  },

  deleteGoods: function(e) {
    var id = e.currentTarget.dataset.id;
    wx.showModal({
      title: '',
      content: '确认下架？',
      success: function(res) {
        if (res.confirm) {
          util.request(api.GoodsDelte, {
            id: id,
          }, 'POST').then(function(res) {
            if (res.errno === 0) {
              wx.showToast({
                title: '下架成功',
                icon: 'success',
              });
              setTimeout(function() {
                util.redirect('/pages/home/post/post')
              }, 1000)
              
            }
          })
        }
      }
    })
  },

  addGoods: function (e) {
    var that = this;
    var id = e.currentTarget.dataset.id;
    wx.showModal({
      title: '',
      content: '确认上架？',
      success: function (res) {
        if (res.confirm) {
          util.request(api.GoodsAdd, {
            id: id,
          }, 'POST').then(function (res) {
            if (res.errno === 0) {
              
              wx.showToast({
                title: '上架成功',
                icon: 'success',
              });
              setTimeout(function () {
                util.redirect('/pages/home/post/post')
              }, 1000)

            }
          })
        }
      }
    })
  },

  updateGoods: function (e) {
    var goodsId = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/updateGoods/updateGoods?id=' + goodsId,
    })
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