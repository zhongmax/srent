// pages/release/release.js 1
var app = getApp();
var util = require('../../utils/util.js');
var api = require('../../config/api.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    hasLogin: false,
    desc: '',
    rentPrice: 0,
    hasPicture: false,
    picUrls: [],
    files: [],
    name: '',
    catId: 0,
    catName: '未选择',
    goods_sn: 0,
    keyworks: '',
    goodsInfoList: [],
  },

  // 登录
  goLogin() {
    wx.navigateTo({
      url: '/pages/auth/login/login?flag=' + '0',
    });
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var goodsId = parseInt(options.id);
    this.getGoodsInfo(goodsId);
  },

  getGoodsInfo(goodsId) {
    //console.log(goodsId);
    var that = this;

    util.request(api.GoodsInfo, {
      goodsId: goodsId,
    }).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          goodsInfoList: res.data,
          catId: res.data.categoryId,
          files: res.data.gallery,
          rentPrice: res.data.rentPrice,
          name: res.data.name,
          desc: res.data.desc,
          catName: res.data.keywords,
          goods_sn: res.data.goodsSn,
          picUrls: res.data.gallery,
          deposit: res.data.deposit,
        })
      
      }
    });

  },

  previewImage: function (e) {
    console.log(e.currentTarget.dataset.idx);
    console.log(this.data.files[e.currentTarget.dataset.idx]);
    wx.previewImage({
      current: this.data.files[e.currentTarget.dataset.idx],
      urls: this.data.files, // 需要预览的图片http链接列表
    })
  },

  chooseImage: function (e) {
    // if (this.data.files.length >= 8) {
    //   util.showErrorToast('只能上传五张图片')
    //   return false;
    // }

    var that = this;
    wx.chooseImage({
      count: 8,
      sizeType: ['original', 'compressed'],
      sourceType: ['album', 'camera'],
      success: function (res) {
        const files = that.data.files.concat(res.tempFilePaths)
        // that.setData({
        //   files: that.data.files.concat(res.tempFilePaths)
        // });
        // that.setData({
        //   files: files
        // })
        // console.log(that.data.files);
        if (files.length <= 8) {
          that.setData({
            files: files
          })
        } else {
          that.setData({
            files: files.slice(0, 8)
          })
        }

        // that.data.files = files.length <= 8 ? files : files.slice(0, 8);
        that.upload(res);
      }
    })
  },

  upload: function (res) {
    var that = this;
    console.log(res.tempFilePaths.length);
    for (var i = 0; i < res.tempFilePaths.length; i++) {
      const uploadTask = wx.uploadFile({
        url: api.StorageUpload,
        filePath: res.tempFilePaths[i],
        name: 'file',
        success: function (res) {
          var _res = JSON.parse(res.data);
          if (_res.errno === 0) {
            var url = _res.data.url
            that.data.picUrls.push(url)
            that.setData({
              hasPicture: true,
              picUrls: that.data.picUrls
            })
          }
        },
        fail: function (e) {
          wx.showModal({
            title: '错误',
            content: '上传失败',
            showCancel: false
          })
        }
      })
      uploadTask.onProgressUpdate((res) => {
        console.log('上传进度', res.progress)
        console.log('已经上传的数据长度', res.totalBytesSent)
        console.log('预期需要上传的数据总长度', res.totalBytesExpectedToWrite)
      })
    }

    // var _res = JSON.parse(res.data);
    // that.data.picUrls.push(url)
    // that.setData({
    //   hasPicture: true,
    //   picUrls: that.data.picUrls
    // })

  },

  bindTitleInput: function (event) {
    let value = event.detail.value;

    if (value && value.length > 40) {
      return false;
    }

    this.setData({
      name: event.detail.value,
    });

  },

  bindInputValue(event) {

    let value = event.detail.value;

    //判断是否超过140个字符
    // if (value && value.length > 140) {
    //   return false;
    // }

    this.setData({
      desc: event.detail.value,
    })
  },

  bindPriceInput: function (event) {
    let value = parseInt(event.detail.value);

    this.setData({
      rentPrice: value,
    });
  },

  bindCat: function (event) {
    wx.navigateTo({
      url: '/pages/updateGoods/category/category',
    })
  },

  onPost: function () {
    let that = this;

    if (this.data.name === '') {
      util.showErrorToast('请填写名称');
      return false;
    }

    if (this.data.rentPrice == 0) {
      util.showErrorToast('请填写租金');
      return false;
    }

    if (this.data.files.length === 0) {
      util.showErrorToast('请选择图片');
      return false;
    }

    if (this.data.desc === '') {
      util.showErrorToast('请填写描述');
      return false;
    }

    if (this.data.catName === '未选择') {
      util.showErrorToast('请选择分类');
      return false;
    }

    util.request(api.GoodsPost, {
      rentPrice: that.data.rentPrice,
      name: that.data.name,
      desc: that.data.desc,
      categoryId: that.data.catId,
      keywords: that.data.catName,
      gallery: that.data.picUrls,
      id: that.data.goodsInfoList.id,
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        wx.showToast({
          title: '修改成功',
          icon: 'success',
          success: function () {
            setTimeout(function() {
              wx.redirectTo({
                url: '/pages/home/post/post',
              })
            }, 1000);
          },
        })
      }
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.setData({
      hasLogin: app.globalData.hasLogin,
    })

  },

  removeImage(e) {
    const idx = e.target.dataset.idx
    console.log(idx);
    let files = this.data.files;
    files.splice(idx, 1);
    let picUrls = this.data.picUrls;
    picUrls.splice(idx, 1);
    this.setData({
      files,
      picUrls,
    });
  },

  handleImagePreview(e) {
    const idx = e.target.dataset.idx
    const images = this.data.images

    wx.previewImage({
      current: images[idx],
      urls: images,
    })
  },

})