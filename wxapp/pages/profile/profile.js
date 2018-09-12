// pages/profile/profile.js
var JsonUtils = require('../../utils/JsonUtils.js')
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

  //登陆微信接口服务器
  bindLoginWeixinServer: function() {
    wx.login({
      success: res => {
        // ------ 获取凭证 ------
        var code = res.code;
        if (code) {
          // console.log('获取用户登录凭证：' + code);
          // ------ 发送凭证 ------
          //买家请求服务器登陆微信接口服务器
          var data = {
            action: "buyer@mp_login_wx_server",
            data: code
          }
          net.sendData(data, function(res) {
            console.log(res.data)
          })
          wx.request({
            url: '后台通过获取前端传的code返回openid的接口地址',
            data: {
              code: code
            },
            method: 'POST',
            header: {
              'content-type': 'application/json'
            },
            success: function(res) {
              if (res.statusCode == 200) {
                // console.log("获取到的openid为：" + res.data)
                // that.globalData.openid = res.data
                wx.setStorageSync('openid', res.data)
              } else {
                console.log(res.errMsg)
              }
            },
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