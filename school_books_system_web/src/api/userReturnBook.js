import http from '../utils/http'

/**
 *  api 前缀
 */
const serviceId = '/userReturnBookInfo'

export default {

  page (data) {
    const url = `${serviceId}/page`
    return http.get(url, data)
  },
  userReturnBook (data) {
    const url = `${serviceId}/userReturnBook`
    return http.get(url, data)
  }
}
