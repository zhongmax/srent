// pages/home/rentDetail/rentDetail.js
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    orderId: 0,
    orderInfo: {},
    orderGoods: [],
    expressCompany: "",
    expressNumber: "",
    userId: 0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      orderId: options.id
    });
    this.getOrderDetail();
  },

  getOrderDetail: function() {
    wx.showLoading({
      title: '加载中',
    });

    setTimeout(function() {
      wx.hideLoading()
    }, 2000);

    let that = this;
    util.request(api.OrderDetail, {
      orderId: that.data.orderId
    }).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          orderInfo: res.data.orderInfo,
          orderGoods: res.data.orderGoods,
        });
      }
      wx.hideLoading();
    })
  },

  submitShip: function() {
    let that = this;
    if (this.data.expressCompany === "" || this.data.expressNumber === "")    {
      util.showErrorToast('请填入快递信息');
      return false;
    }

    wx.showModal({
      title: '',
      content: '确认快递信息无误？',
      success: function (res) {
        if (res.confirm) {
          util.request(api.OrderShip, {
            orderId: that.data.orderId,
            expressCompany: that.data.expressCompany,
            expressNumber: that.data.expressNumber,
          }, 'POST').then(function (res) {
            if (res.errno === 0) {
              wx.showToast({
                title: '填写完成',
                icon: 'success',
              });
              setTimeout(function() {
                util.redirect('/pages/home/rent/rent');
              }, 1000)
              
            }
          })
        }
      }
    })

    
  },

  bindShipCompany: function (event) {
    let value = event.detail.value;

    if (value && value.length > 40) {
      return false;
    }

    this.setData({
      expressCompany: event.detail.value,
    });

  },

  bindShipNumber: function (event) {
    let value = event.detail.value;

    if (value && value.length > 40) {
      return false;
    }

    this.setData({
      expressNumber: event.detail.value,
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