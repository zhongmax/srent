// pages/search/search.js
var util = require('../../utils/util.js');
var api = require('../../config/api.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    searchStatus: false,
    goodsList: [],
    helpKeyword: [],
    historyKeyword: [],
    defaultKeyword: {},
    hotKeyword: [],
    currentSort: 'name',
    currentSortType: 'default',
    currentSortOrder: 'desc',
    page: 1,
    limit: 20,
    categoryId: 0,
    categoryFilter: false,
    filterCategory: [],
  },

  // 事件处理
  closeSearch: function() {
    wx.navigateBack()
  },

  clearKeyword: function() {
    this.setData({
      keyword: '',
      searchStatus: false,
    });
  },

  inputChange: function (e) {
    this.setData({
      keyword: e.detail.value,
      searchStatus: false,
    });
  },

  inputFocus: function() {
    this.setData({
      searchStatus: false,
      goodList: []
    });
  },

  onKeywordConfirm(event) {
    this.getSearchResult(event.detail.value);
  },

  getSearchResult(keyword) {
    if (keyword === '') {
      keyword = this.data.defaultKeyword.keyword;
    }

    this.setData({
      keyword: keyword,
      page: 1,
      goodList: [],
    })

    this.getGoodsList();
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getSearchKeyword();
  },

  getSearchKeyword() {
    let that = this;
    util.request(api.SearchIndex).then(function (res) {
      if (res.errno === 0) {
        that.setData({
          historyKeyword: res.data.historyKeywordList,
          defaultKeyword: res.data.defaultKeyword,
          hotKeyword: res.data.hotKeywordList,
        })
      }
    });
  },

  getGoodsList: function() {
    let that = this;
    util.request(api.GoodsList, {
      keyword: that.data.keyword,
      page: that.data.page,
      limit: that.data.limit,
      sort: that.data.currentSort,
      order: that.data.currentSortOrder,
      categoryId: that.data.categoryId,
    }).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          searchStatus: true,
          goodsList: res.data.list,
          filterCategory: res.data.filterCategoryList
        });
      }
      // 重新获取关键词
      that.getSearchKeyword();
    });
  },

  clearHistory: function() {
    this.setData({
      historyKeyword: [],
    })

    util.request(api.SearchClearHistory, {}, 'POST')
    .then(function(res) {

    });
  },

  // 点击热门搜索的词条为关键词进行搜索
  onKeywordTap: function(event) {
    this.getSearchResult(event.target.dataset.keyword);
  },

  openSortFilter: function(event) {
    let currentId = event.currentTarget.id;
    switch (currentId) {
      case 'categoryFilter':
        this.setData({
          categoryFilter: !this.data.categoryFilter,
          currentSortType: 'category',
          currentSort: 'add_time',
          currentSortOrder: 'desc'
        });
        break;
      case 'priceSort':
        let tmpSortOrder = 'asc';
        if (this.data.currentSortOrder == 'asc') {
          tmpSortOrder = 'desc';
        }
        this.setData({
          currentSortType: 'price',
          currentSort: 'rent_price',
          currentSortOrder: tmpSortOrder,
          categoryFilter: false
        });

        this.getGoodsList();
        break;
      default:
        //综合排序
        this.setData({
          currentSortType: 'default',
          currentSort: 'name',
          currentSortOrder: 'desc',
          categoryFilter: false,
          categoryId: 0,
        });
        this.getGoodsList();
    }
  },

  selectCategory: function (event) {
    let currentIndex = event.target.dataset.categoryIndex;
    let filterCategory = this.data.filterCategory;
    let currentCategory = null;
    for (let key in filterCategory) {
      if (key == currentIndex) {
        filterCategory[key].selected = true;
        currentCategory = filterCategory[key];
      } else {
        filterCategory[key].selected = false;
      }
    }
    this.setData({
      filterCategory: filterCategory,
      categoryFilter: false,
      categoryId: currentCategory.id,
      page: 1,
      goodsList: []
    });
    this.getGoodsList();
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