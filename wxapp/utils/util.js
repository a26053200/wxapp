const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

const showTips = (msg, callback) => {
  wx.showToast({
    title: msg,
    icon: 'none',
    duration: 2000,
    complete: function () {
      if (callback)
        callback();
    }
  })
}

// 数据单位化
const numberUint = (num, decimal, uint) => {
  var res = num / decimal;
  return Math.round(res) + uint;
}

const isRealNum = val => {
  // isNaN()函数 把空串 空格 以及NUll 按照0来处理 所以先去除
  if (val === "" || val == null) {
    return false;
  }
  if (!isNaN(val)) {
    return true;
  } else {
    return false;
  }
}

const isEmpty = e => {
  switch (e) {
    case "":
    case 0:
    case "0":
    case null:
    case false:
    case typeof this == "undefined":
      return true;
    default:
      return false;
  }
}

module.exports = {
  formatTime: formatTime,
  showTips: showTips,
  numberUint: numberUint,
  isRealNum: isRealNum,
  isEmpty: isEmpty,
}