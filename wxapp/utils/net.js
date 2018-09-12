/**
  * json转字符串
  */
function sendData(data, callback, failCallback) {
  data.server = "BusinessServer"
  wx.request({
    url: 'http://127.0.0.1:8080/',
    data: data,
    header: {
      'content-type': 'application/json' // 默认值
    },
    method: 'POST',
    success: function (res) {
      console.log("[rspd]" + res.data)
      if (callback != null && callback != undefined)
        callback(res.data);
    },
    fail:function(){
      if (failCallback != null && failCallback != undefined)
        failCallback();
    }
  })
}

module.exports = {
  sendData: sendData
}