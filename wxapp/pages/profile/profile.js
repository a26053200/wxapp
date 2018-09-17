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
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  onLoad: function () {
    
  },
  onLoad: function() {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },

  bindLoginWeixinServer: function() {
    var _this = this;
    // 先探测服务器是否正常开启
    net.sendData({
      action: "Buyer@mp_probe",
    }, function(res) {
      //console.log(res);
      if (res.data.probe_state) {
        console.log("服务器正常运行");
        _this.doLoginWeixinServer();
      } else {
        console.log("服务器正在维护");
      }
    })
  },

  //登陆微信接口服务器
  doLoginWeixinServer: function() {
    wx.login({// ------ 获取凭证 ------
      success: res => {
        var code = res.code;
        if (code) {
          //买家请求服务器登陆微信接口服务器
          var data = {
            action: "Buyer@mp_login_wx_server",
            code: code
          }
          net.sendData(data, function(res) {
            if (res.errcode == null || res.errcode == undefined)
            {//成功获取
              // console.log("获取到的openid为：" + res.data)
              // that.globalData.openid = res.data
              //wx.setStorageSync('openid', res)
            }else{
              //"errcode": 40029, "errmsg": "invalid code"
              switch (errcode)
              {
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
  //获取个人信息
  bindGetProfileInfo: function() {
    var data = {
      action: "mp_get_profile",
      userNicknam: app.globalData.userInfo.nickName
    }
    net.sendData(data, function(res) {
      //console.log(res.data)
    })
  },

  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  }

})