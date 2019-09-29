// pages/home/address/address.js
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    addressList: [],
    total: 0
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
    this.getAddressList();
  },

  getAddressList() {
    let that = this;
    util.request(api.AddressList).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          addressList: res.data.list,
          total: res.data.total
        })
      }
    })
  },

  addressAddOrUpdate(event) {
    //console.log(event)

    //返回之前，先取出上一页对象，并设置addressId
    var pages = getCurrentPages();
    var prevPage = pages[pages.length - 2];

    if (prevPage.route == "pages/checkout/checkout") {
      try {
        wx.setStorageSync('addressId', event.currentTarget.dataset.addressId);
      } catch (e) {

      }

      let addressId = event.currentTarget.dataset.addressId;
      if (addressId && addressId != 0) {
        wx.navigateBack();
      } else {
        wx.navigateTo({
          url: '/pages/home/addressAdd/addressAdd?id=' + addressId
        })
      }

    } else {
      wx.navigateTo({
        url: '/pages/home/addressAdd/addressAdd?id=' + event.currentTarget.dataset.addressId
      })
    }
  },
  deleteAddress(event) {
    console.log(event.target)
    let that = this;
    wx.showModal({
      title: '',
      content: '确定要删除地址？',
      success: function (res) {
        if (res.confirm) {
          let addressId = event.target.dataset.addressId;
          util.request(api.AddressDelete, {
            id: addressId
          }, 'POST').then(function (res) {
            if (res.errno === 0) {
              that.getAddressList();
              wx.removeStorage({
                key: 'addressId',
                success: function (res) { },
              })
            }
          });
          console.log('用户点击确定')
        }
      }
    })
    return false;

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