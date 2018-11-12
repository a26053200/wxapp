/**
  * json转字符串
  */
function sendData(data, callback, failCallback) {
  data.server = "BusinessServer";
  console.log("[send]" + JSON.stringify(data));
  wx.request({
    url: 'http://127.0.0.1:8090/',
    data: data,
    header: {
      'content-type': 'application/json' // 默认值
    },
    method: 'POST',
    success: function (res) {
      if (callback != null && callback != undefined)
      {
        //var jsonStr = res.data.substring(0, res.data.length - 1)//去掉最后一位空字符
        console.log("[rspd]" + JSON.stringify(res.data))
        //var json = JSON.parse(jsonStr)
        callback(res.data);
      }
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