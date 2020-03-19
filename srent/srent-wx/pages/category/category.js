// pages/category/category.js
var util = require('../../utils/util.js');
var api = require('../../config/api.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    categoryList: [],
    currentCategory: {},
    currentSubCategoryList: {},
    scrollLeft: 0,
    scrollTop: 0,
    goodsCount: 0,
    scrollHeight: 0,
    allCategory: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getCatalog();
  },

  getCatalog: function() {
    let that = this;
    wx.showLoading({
      title: '加载中...',
    });
    util.request(api.CatalogList).then(function(res) {
      that.setData({
        categoryList: res.data.categoryList,
        currentCategory: res.data.currentCategory,
        currentSubCategoryList: res.data.currentSubCategory
      });
      wx.hideLoading();
    });

    util.request(api.CategoryAll).then(function (res) {
      if (res.errno === 0) {
        that.setData({
          allCategory: res.data.allCategoryList,
        })
      }
    });
  },

  getCurrentCategory: function(id) {
    let that = this;
    util.request(api.CatalogCurrent, {
      id: id
    }).then(function(res) {
      that.setData({
        currentCategory: res.data.currentCategory,
        currentSubCategoryList: res.data.currentSubCategory
      });
    });
  },

  switchCate: function(event) {
    var that = this;
    var currentTarget = event.currentTarget;
    if (this.data.currentCategory.id == event.currentTarget.dataset.id) {
      return false;
    }

    this.getCurrentCategory(event.currentTarget.dataset.id);
  },

  goBack: function(event) {
    var that = this;
    var pages = getCurrentPages();
    var prevPage = pages[pages.length - 2];
    var id = parseInt(event.currentTarget.dataset.id);
    var currentSubCategoryList = this.data.currentSubCategoryList;
    var allCatList = this.data.allCategory;
    // console.log(id + ',' + allCatList[id-1].name);
    prevPage.setData({
      catId: id,
      catName: allCatList[id-1].name,
    });
    wx.switchTab({
      url : "/pages/release/release",
    })
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