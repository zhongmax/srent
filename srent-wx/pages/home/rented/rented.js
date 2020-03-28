// pages/home/rented/rented.js
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    orderList: [],
    showType: 0,
    page: 1,
    limit: 10,
    totalPages: 1,
    dateNow: '',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    let that = this
    try {
      var tab = wx.getStorageSync('tab');

      this.setData({
        showType: tab
      });
    } catch (e) {

    }

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
    this.getOrderList();
  },

  getOrderList() {
    let that = this;
    util.request(api.OrderList, {
      showType: that.data.showType,
      page: that.data.page,
      limit: that.data.limit
    }).then(function(res) {
      if (res.errno === 0) {
        console.log(res.data);
        that.setData({
          orderList: that.data.orderList.concat(res.data.list),

          totalPages: res.data.pages
        });
      }
    });
    var time = util.formatDate(new Date());
    this.setData({
      dateNow: time,
    })
  },

  cancelOrder: function(event) {
    let that = this;
    var id = event.currentTarget.dataset.id;

    wx.showModal({
      title: '订单',
      content: '确认要取消订单吗？',
      success(res) {
        if (res.confirm) {
          util.request(api.OrderCancel, {
            orderId: that.data.orderList[id].id,
          }, 'POST').then(function(res) {
            if (res.errno === 0) {
              wx.showToast({
                title: '取消成功',
                icon: 'success',
                success: function() {
                  setTimeout(function() {
                    wx.redirectTo({
                      url: '/pages/home/rented/rented',
                    })
                  }, 1000);
                },
              })
            }
          });

        } else if (res.cancel) {

        }
      }
    });

  },

  submitOrder: function(event) {
    let that = this;
    var id = parseInt(event.currentTarget.dataset.id);
    var date = this.data.orderList[id].date;

    wx.showModal({
      title: '',
      content: '归还日期为' + date,
      showCancel: false,
      confirmText: "确认开租",
      success(res) {
        if (res.confirm) {
          console.log("点击");
          util.request(api.OrderRent, {
            orderId: that.data.orderList[id].id,
          }, 'POST').then(function(res) {
            if (res.errno === 0) {
              wx.showToast({
                title: '租赁成功',
                icon: 'success',
                success: function() {
                  setTimeout(function() {
                    wx.redirectTo({
                      url: '/pages/home/rented/rented',
                    })
                  }, 1000);
                },
              })
            }
          });
        }
      }
    });
  },

  returnOrder: function (event) {
    let that = this;
    var id = parseInt(event.currentTarget.dataset.id);

    util.request(api.OrderReturn, {
      orderId: that.data.orderList[id].id,
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        
      }
    });
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