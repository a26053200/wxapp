// pages/profile/profile.js
var JsonUtils = require('../../utils/JsonUtils.js')
var util = require('../../utils/util.js')
var net = require('../../utils/net.js')
//获取应用实例
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    app:app,
    userInfo: {},
    profileInfo: null,
    buyerInfo: null,
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    scanId:"undefined"
  },

  onLoad: function() {
    var _this = this
    if (app.globalData.userInfo) {
      this.onUserInfo(app.globalData.userInfo)
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        _this.onUserInfo(res.userInfo)
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          _this.onUserInfo(res.userInfo)
        }
      })
    }
  },

  getUserInfo: function(e) {
    app.globalData.userInfo = e.detail.userInfo
    onUserInfo(e.detail.userInfo)
  },

  onUserInfo: function(userInfo) {
    console.log("获取微信用户信息" + userInfo.nickName);
    this.setData({
      userInfo: userInfo,
      hasUserInfo: true
    })
    this.probeServer();
  },
  /**
    * 生命周期函数--监听页面显示
    */
  onShow: function () {
    //this.probeServer();
  },
  /**
  * 生命周期函数--监听页面隐藏
  */
  onHide: function () {

  },
  // 探测服务器状态
  probeServer: function() {
    var _this = this;
    if (app.globalData.profileInfo)
      return
    // 先探测服务器是否正常开启
    net.sendData({
      action: "buyer@mp_probe"
    }, function(res) {
      //console.log(res);
      if (res.data.probe_state) {
        console.log("服务器正常运行");
        _this.loginWeixinServer();
      } else {
        console.log("服务器正在维护");
      }
    })
  },

  //登陆微信接口服务器
  loginWeixinServer: function() {
    var _this = this;
    wx.login({ // ------ 获取凭证 ------
      success: res => {
        var code = res.code;
        if (code) {
          //买家请求服务器登陆微信接口服务器
          var data = {
            action: "buyer@mp_login_wx_server",
            code: code,
            wxUserInfo: _this.data.userInfo
          }
          net.sendData(data, function(res) {
            if (res.errcode == null || res.errcode == undefined) { //成功获取,服务器返回买家信息
              _this.setData({
                profileInfo: res.data.profile_info,
                buyer_info: res.data.buyer_info
              })
              app.globalData.profileInfo = res.data.profile_info;
              app.globalData.buyer_info = res.data.buyer_info;
              _this.onBuyerInfo();
            } else {
              //"errcode": 40029, "errmsg": "invalid code"
              switch (errcode) {
                case -1:
                  util.showTips("系统繁忙，此时请开发者稍候再试");
                  break;
                case 0:
                  util.showTips("请求成功");
                  break;
                case 40029:
                  util.showTips("code 无效");
                  break;
                case 45011:
                  util.showTips("频率限制，每个用户每分钟100次");
                  break;
                default:
                  break;
              }
            }
          })
        } else {
          console.log('获取用户登录失败：' + res.errMsg);
        }
      }
    })
  },

  onBuyerInfo: function(){
    
  },

  bindScanCodeLogin : function(){
    var _this = this;
    // 只允许从相机扫码
    wx.scanCode({
      onlyFromCamera: false,
      success(res) {
        //console.log(res)
        _this.notifyWebClientLogin(res.result)
      }
    })
  },

  notifyWebClientLogin: function (scan_id){
    var _this = this;
    //买家请求服务器登陆微信接口服务器
    var data = {
      action: "seller@mp_scan_web_login",
      profile_id: this.data.profileInfo.profile_id,
      scan_id: scan_id,
      scan_state: "Accept"
    }
    net.sendData(data, function (res) {
      _this.setData({
        scanId: scan_id
      })
      console.log("scan_state：" + res.data.scan_state);
    }); 
  }
})