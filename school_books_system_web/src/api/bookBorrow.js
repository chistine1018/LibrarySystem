import http from '../utils/http'

/**
 *  api 前缀
 */
const serviceId = '/bookBorrowInfo'

export default {

  page (data) {
    const url = `${serviceId}/page`
    return http.get(url, data)
  },
  bookBorrow (data) {
    const url = `${serviceId}/bookBorrow`
    return http.get(url, data)
  }
}
