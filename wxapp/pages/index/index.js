//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    second: 5
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function() {
    this.countdown(this, function() {
      console.log("倒计时结束进入主页")
    });
  },
  countdown: function(that, overCallback) {
    var _that = that
    var second = that.data.second
    if (second == 0) {
      if (overCallback != null && overCallback != undefined)
        overCallback()
      return;
    }
    var time = setTimeout(function() {
      _that.setData({
        second: second - 1
      });
      _that.countdown(_that, overCallback);
    }, 1000)
  }
})