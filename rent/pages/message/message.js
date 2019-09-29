// pages/message/message.js
var util = require('../../utils/util.js');
var user = require('../../utils/user.js');
var api = require('../../config/api.js');

var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    id: 0,
    hasLogin: false,
    messageList: [],
    userInfo: [],
  },

  // 按下事件开始
  touchStart: function (e) {
    let that = this;
    that.setData({
      touchStart: e.timeStamp,
    })
  },

  touchEnd: function (e) {
    let that = this;
    that.setData({
      touchEnd: e.timeStamp
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // this.setData({
    //   // 传递JS对象
    //   mess_key: messData.messList,
    // });
    
  },

  onTalkTap: function (event) {
    var that = this;
    var index = event.currentTarget.dataset.index;
    var goodsId = this.data.messageList[index].goodsId;
    var id = this.data.messageList[index].groupId;
    var userId = this.data.messageList[index].userId;

    // 触摸时间距离页面打开的毫秒数
    var touchTime = that.data.touchEnd - that.data.touchStart;
    // 如果按下事件超过350ms
    if (touchTime > 350) {
      wx.showModal({
        title: '',
        content: '确定删除该会话吗？',
        success: function (res) {
          if (res.confirm) {
            util.request(api.MessageDelete, {
              goodsId: goodsId,
            }, 'POST').then(function (res) {
              if (res.errno === 0) {
                wx.showToast({
                  title: '删除成功',
                  icon: 'success',
                  duration: 2000
                });
                that.data.messageList.splice(index, 1);
                that.setData({
                  messageList: that.data.messageList
                });
              }
            })
          }
        }
      })
    } else {
      wx.navigateTo({
        url: '/pages/message/room/room?goodsId=' + goodsId + '&id=' + id + '&userId=' + userId,
      });
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
    this.getMessageList();
  },

  getMessageList() {
    var that = this;
    if (this.data.messageList.length >= 0) {
      this.setData({
        messageList: [],
      })
    }
    util.request(api.MessageList).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          messageList: that.data.messageList.concat(res.data.list),
        })
      }
    });

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