// pages/sort/sort.js
var util = require('../../utils/util.js');
var api = require('../../config/api.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    id: 0,
    scrollHeight: 0,
    scrollLeft: 0,
    scrollTop: 0,
    navList: [],
    currentCategory: {},
    goodsList: [],
    page: 1,
    limit: 10
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    if (options.id) {
      that.setData({
        id: parseInt(options.id)
      })
    }

    wx.getSystemInfo({
      success: function (res) {
        that.setData({
          scrollHeight: res.windowHeight
        });
      },
    });

    this.getCategoryInfo();

  },

  getCategoryInfo: function () {
    let that = this;
    util.request(api.GoodsCategory, {
      id: this.data.id
    }).then(function (res) {
      if (res.errno == 0) {
        that.setData({
          navList: res.data.brotherCategory,
          currentCategory: res.data.currentCategory
        });

        wx.setNavigationBarTitle({
          title: res.data.parentCategory.name,
        });

        // 当id是L1等级时，这里需要重新设置L1分类的一个子类的id
        if (res.data.parentCategory.id == that.data.id) {
          that.setData({
            id: res.data.currentCategory.id
          });
        }

        // nav位置
        let currentIndex = 0;
        let navListCount = that.data.navList.length;
        for (let i = 0; i < navListCount; i++) {
          currentIndex += 1;
          if (that.data.navList[i].id == that.data.id) {
            break;
          }
        }

        if (currentIndex > navListCount / 2 && navListCount > 3) {
          that.setData({
            scrollLeft: currentIndex * 80
          });
        }
        that.getGoodsList();
      } else {
        // error message
      }
    });
  },

  getGoodsList: function () {
    var that = this;

    util.request(api.GoodsList, {
      categoryId: that.data.id,
      page: that.data.page,
      limit: that.data.limit
    }).then(function (res) {
      that.setData({
        goodsList: res.data.list,
      });
    });
  },

  switchCate: function (event) {
    if (this.data.id == event.currentTarget.dataset.id) {
      return false;
    }
    var that = this;
    var clientX = event.detail.x;
    var currentTarget = event.currentTarget;

    if (clientX < 80) {
      that.setData({
        scrollLeft: currentTarget.offsetLeft - 80
      });
    } else if (clientX > 330) {
      that.setData({
        scrollLeft: currentTarget.offsetLeft
      });
    }
    this.setData({
      id: event.currentTarget.dataset.id
    });

    this.getCategoryInfo();
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

  },
})