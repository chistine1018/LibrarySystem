import http from '../utils/http'

/**
 *  api 前綴
 */
const serviceId = '/auth'

export default {

  login (data) {
    const url = `${serviceId}/login`
    return http.post(url, data)
  },

  logOut () {
    const url = `${serviceId}/logOut`
    return http.get(url)
  },

  getAuthByToken () {
    const url = `${serviceId}/getAuthByToken`
    return http.get(url)
  }

  // VueCookie.set('userInfo', JSON.stringify(this.userInfo))
  // JSON.parse(VueCookie.get('userInfo'))
}
