// pages/checkout/checkout.js
var util = require('../../utils/util.js');
var api = require('../../config/api.js');
var user = require('../../utils/user.js');
var check = require('../../utils/check.js');
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    goods: {},
    transactionType: ["见面交易", "寄送快递"],
    type: 0,
    addressId: 0,
    postUserInfo: {},
    postUserId: 0,
    goodsTotalPrice: 0.00,  // 总计
    freightPrice: 0.00,   // 运费
    orderTotalPrice: 0.00, // 实付
    days: 1,
    hasAgree: false,
    time: '12:01',
    address: '',
    phone: '',
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

  PickerChange: function(e) {
    var type = e.detail.value;
    var freightPrice;
    if (type == 0) {
      freightPrice = 0;
    } else {
      freightPrice = 12;
    }
    this.setData({
      type: type,
      freightPrice: freightPrice,
    })
  },

  TimeChange(e) {
    this.setData({
      time: e.detail.value
    })
  },

  onAddressTap(e) {
    this.setData({
      address: e.detail.value
    })
  },

  onPhoneTap(e) {
    this.setData({
      phone: e.detail.value
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    wx.showLoading({
      title: '加载中...',
    });

    try {
      var goods = wx.getStorageSync('goodsInfo');
      if (goods === "") {
        goods = {};
      };

      var postUserInfo = wx.getStorageSync('postUserInfo');

      var postUserId = wx.getStorageSync('postUserId');

      var addressId = wx.getStorageSync('addressId');
      if (addressId === "") {
        addressId = 0;
      }
      
      this.setData({
        
        goods: goods,
        addressId: addressId,
        postUserInfo: postUserInfo,
        postUserId: postUserId,
      });
    } catch(e) {
      console.log(e);
    }
    this.getCheckoutInfo();
  },

  getCheckoutInfo: function() {
    let that = this;
    var type = this.data.type;
    var days = this.data.days;
    var price = this.data.goods.rentPrice;
    var deposit = this.data.goods.deposit;
    var freight = 12;
    if (days >= 3 || type == 0) {
      freight = 0;
    }

    this.setData({
      goodsTotalPrice: days * price,
      freightPrice: freight,
      orderTotalPrice: days * price + freight + deposit,
    });
    util.request(api.BuyCheckout, {
      goodsId: that.data.goods.id,
      addressId: that.data.addressId,
    }).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          addressId: res.data.addressId,
          checkedAddress: res.data.checkedAddress,
        });
      }
      wx.hideLoading();
    });
  },

  bindCalcPrice: function() {
    var days = this.data.days;
    var price = this.data.goods.rentPrice;
    var deposit = this.data.goods.deposit;
    console.log(days);
    var freight = 12;
    if (days >= 3) {
      freight = 0;
    }
    this.setData({
      goodsTotalPrice: days * price,
      freightPrice: freight,
      orderTotalPrice: days * price + freight + deposit,
    })
  },

  bindDaysInput: function(event) {
    var that = this;
    let value = parseInt(event.detail.value);
    if (value > 30) {
      wx.showModal({
        title: '提示',
        content: '租期最多为30天!',
        showCancel: false,
        success(res) {
          that.setData({
            days: 30
          })
        }
      });
    }
    this.setData({
      days: value,
    });
  },

  selectAddress() {
    wx.navigateTo({
      url: '/pages/home/address/address',
    })
  },

  checkboxChange: function(e) {
    if (e.detail.value != "") {
      this.setData({
        hasAgree: true,
      })
    } else {
      this.setData({
        hasAgree: false,
      })
    }
  },

  submitOrder: function() {
    if (this.data.type == 1 && this.data.addressId <= 0) {
      util.showErrorToast('请选择收货地址');
      return false;
    }

    if (this.data.type == 0 && this.data.address == '') {
      util.showErrorToast('请选择见面位置');
      return false;
    }

    if (this.data.days <= 0) {
      util.showErrorToast('租期最少为一天');
      return false;
    }

    if (this.data.hasAgree == false) {
      util.showErrorToast('请勾选协议');
      return false;
    };

    if (this.data.type == 0) {
      if (!check.isValidPhone(this.data.phone)) {
        util.showErrorToast('手机号不正确');
        return false;
      }
      util.request(api.OrderSubmit, {
        address: this.data.address,
        goodsId: this.data.goods.id,
        days: this.data.days,
        phone: this.data.phone,
        time: this.data.time,
        type: this.data.type,
        postUserId: this.data.postUserId,
        goodsTotalPrice: this.data.goodsTotalPrice,
        freightPrice: this.data.freightPrice,
        orderTotalPrice: this.data.orderTotalPrice,
      }, 'POST').then(res => {
        if (res.errno === 0) {
          const orderId = res.data.orderId;

          wx.redirectTo({
            url: '/pages/payResult/payResult?status=1&orderId=' + orderId
          });
        } else {
          wx.redirectTo({
            url: '/pages/payResult/payResult?status=0&orderId=' + orderId
          });
        }
      })
    } else {
      util.request(api.OrderSubmit, {
        type: this.data.type,
        postUserId: this.data.postUserId,
        goodsId: this.data.goods.id,
        days: this.data.days,
        addressId: this.data.addressId,
        goodsTotalPrice: this.data.goodsTotalPrice,
        freightPrice: this.data.freightPrice,
        orderTotalPrice: this.data.orderTotalPrice,
      }, 'POST').then(res => {
        if (res.errno === 0) {
          const orderId = res.data.orderId;

          wx.redirectTo({
            url: '/pages/payResult/payResult?status=1&orderId=' + orderId
          });
        } else {
          wx.redirectTo({
            url: '/pages/payResult/payResult?status=0&orderId=' + orderId
          });
        }
      })
    }
    
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