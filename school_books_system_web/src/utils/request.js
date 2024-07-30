/** **   request.js   ****/
// 導入axios
import axios from 'axios'
import VueCookie from 'vue-cookie'
import { Message } from 'element-ui'
// 使用element-ui Message做消息提醒
// 1. 創建新的axios實例，
const service = axios.create({
  // 公共接口--這裡注意後面會講
  baseURL: 'http://localhost:9292',
  // baseURL: 'https://j3code.cn/school_book_api',
  // 超時時間 單位是ms，這裡設置了3s的超時時間
  timeout: 30 * 1000
})
// 2.請求攔截器
service.interceptors.request.use(config => {
  // 發送請求前的一些處理，數據便轉化，配置請求頭，設置token，設置loading等，根據需求去添加
  // eslint-disable-next-line no-unused-expressions
  config.data instanceof FormData ? config.data : JSON.stringify(config.data)
  if (config.headers === undefined || config.headers === null) {
    config.headers = {
      'Content-Type': 'application/x-www-form-urlencoded' // 配置请求头
    }
  }

  // 文章：https://blog.csdn.net/qq_42767631/article/details/84108541
  // 資料：https://www.npmjs.com/package/vue-cookie
  const token = VueCookie.get('Authorization')
  if (token) {
    config.headers.Authorization = token // 如果要求攜帶在請求頭中
    VueCookie.set('Authorization', token, 5)
    VueCookie.set('authInfo', VueCookie.get('authInfo'), 5)
  }
  return config
}, error => {
  Promise.reject(error)
})

// 3.響應攔截器
service.interceptors.response.use(response => {
  // 接收到響應數據並成功後的一些共有的處理，關閉loading等
  if (response.data.code === 401) {
    const token = VueCookie.get('Authorization')
    if (token === undefined || token === null) {
      Message.error('操作失敗，請登錄訪問！')
    } else {
      VueCookie.delete('Authorization')
      Message.error('登陸失效，請退出重登！')
    }
  } else if (!response.data.result) {
    Message.error(response.data.message + ':' + response.data.exception)
  }
  return response.data
}, error => {
  console.log('error', error)
  /** *** 接收到異常響應的處理開始 *****/
  if (error && error.response) {
    error.message = error.response.data.exception
  } else {
    // 超時處理
    if (JSON.stringify(error).includes('timeout')) {
      Message.error('服務器響應超時，請刷新當前頁')
    }
    error.message = '連接服務器失敗'
  }

  Message.warn(error.message)
  if (error.response.status === 401) {
    VueCookie.delete('Authorization')
    VueCookie.delete('userInfo')
  }
  /** *** 處理結束 *****/
  // 如果不需要錯誤處理，以上的處理過程都可省略
  return Promise.resolve(error.response)
})
// 4.導入文件
export default service
