import http from '../utils/http'

/**
 *  api 前缀
 */
const serviceId = '/bookCategory'

export default {

  page (data) {
    const url = `${serviceId}/page`
    return http.get(url, data)
  },
  saveOrUpdate (data) {
    const url = `${serviceId}/saveOrUpdate`
    return http.get(url, data)
  }
}
