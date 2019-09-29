// 本机开发使用
// var WxApiRoot = 'http://localhost:8080/wx/';
// 局域网测试使用
// var WxApiRoot = 'http://192.168.10.118:8080/wx/';
// var WxApiRoot = 'http://192.168.43.220:8080/wx/';
// 上线
var WxApiRoot = 'https://csmaxwell.xyz/wx/';

module.exports = {
  IndexUrl: WxApiRoot + 'home/index', // 首页数据接口

  CatalogList: WxApiRoot + 'catalog/index', //分类目录全部分类数据接口
  CatalogCurrent: WxApiRoot + 'catalog/current', // 分类目录当前分类数据接口
  CategoryAll: WxApiRoot + 'catalog/list', // 获取所有分类

  OrderDetail: WxApiRoot + 'order/detail', //订单详情
  OrderSubmit: WxApiRoot + 'order/submit', // 提交订单
  OrderList: WxApiRoot + 'order/list', //订单列表
  OrderShip: WxApiRoot + 'order/ship', // 填写快递信息
  OrderPost: WxApiRoot + 'order/post', // 获取发布
  OrderRent: WxApiRoot + 'order/rent', // 开始租赁
  RentList: WxApiRoot + 'order/rent', // 获取我出租的
  OrderCancel: WxApiRoot + 'order/cancel', // 取消订单
  OrderReturn: WxApiRoot + 'order/ritem', // 归还商品

  AddressList: WxApiRoot + 'address/list', // 收货地址列表
  AddressDetail: WxApiRoot + 'address/detail', // 收货地址详情
  AddressSave: WxApiRoot + 'address/save', //保存收货地址
  AddressDelete: WxApiRoot + 'address/delete', // 删除收货地址
  
  AuthLoginByWeixin: WxApiRoot + 'auth/login_by_weixin', //微信登录
  AuthLoginByAccount: WxApiRoot + 'auth/login', //账号登录
  AuthLogout: WxApiRoot + 'auth/logout', //账号登出
  AuthRegister: WxApiRoot + 'auth/register', //账号注册
  AuthRegisterCaptcha: WxApiRoot + 'auth/regCaptcha', //验证码
  AuthReset: WxApiRoot + 'auth/reset', //账号密码重置

  SearchIndex: WxApiRoot + 'search/index', //搜索关键字
  SearchClearHistory: WxApiRoot + 'search/clearhistory', //搜索历史清楚

  BuyCheckout: WxApiRoot + 'buy/checkout', // 下单前信息确认

  GoodsList: WxApiRoot + 'goods/list', //获得商品列表
  GoodsInfo: WxApiRoot + 'goods/info', // 获得商品信息
  GoodsDelte: WxApiRoot + 'goods/delete', // 商品下架
  GoodsAdd: WxApiRoot + 'goods/add', // 商品上架
  GoodsPost: WxApiRoot + 'goods/post', // 发布商品
  GoodsCategory: WxApiRoot + 'goods/category', //获得分类数据
  
  GoodsDetail: WxApiRoot + 'goods/detail', // 获得商品的详情

  CollectList: WxApiRoot + 'collect/list', //收藏列表
  CollectAddOrDelete: WxApiRoot + 'collect/addordelete', //添加或取消收藏

  FootprintList: WxApiRoot + 'footprint/list', //足迹列表
  FootprintDelete: WxApiRoot + 'footprint/delete', //删除足迹
  
  StorageUpload: WxApiRoot + 'storage/upload', //图片上传,

  MessageAdd: WxApiRoot + 'message/add', // 判断消息是否添加
  MessageList: WxApiRoot + 'message/list', // 获得消息列表
  MessageDelete: WxApiRoot + 'message/delete', // 删除消息列表

  ChatAdd: WxApiRoot + 'chat/add', // 发送消息
  ChatList: WxApiRoot + 'chat/list', // 消息列表
}