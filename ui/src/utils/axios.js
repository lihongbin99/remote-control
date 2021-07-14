import _axios from 'axios'

_axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
_axios.defaults.withCredentials=true
_axios.defaults.crossDomain=true
// 创建axios实例
const axios = _axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: 'http://192.168.1.111:13520/android/',
})

export default axios;