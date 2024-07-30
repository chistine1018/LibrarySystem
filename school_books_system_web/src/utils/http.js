/** **   http.js   ****/
// 導入封裝好的axios實例
import request from './request'

const http = {
  /**
   * methods: 請求
   * @param url 請求地址
   * @param params 請求參數
   */
  get (url, params) {
    const config = {
      method: 'get',
      url: url
    }
    if (params) config.params = params
    return request(config)
  },
  post (url, params, headers) {
    const config = {
      method: 'post',
      url: url
    }
    if (params) {
      // console.log('====---===---0')
      config.data = params
    }
    if (headers) {
      // console.log('====---===---1')
      config.headers = headers
    }
    return request(config)
  },
  put (url, params) {
    const config = {
      method: 'put',
      url: url
    }
    if (params) config.params = params
    return request(config)
  },
  delete (url, params) {
    const config = {
      method: 'delete',
      url: url
    }
    if (params) config.params = params
    return request(config)
  }
}
// 導出
export default http
