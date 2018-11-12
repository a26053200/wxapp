//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    second: 1
  },

  onLoad: function() {
    this.countdown(this, function() {
      wx.reLaunch({
        url: '../home/home',
      })
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