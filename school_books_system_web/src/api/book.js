import http from '../utils/http'

/**
 *  api 前缀
 */
const serviceId = '/book'

export default {

  page (data) {
    const url = `${serviceId}/page`
    return http.get(url, data)
  },
  saveOrUpdate (data) {
    const url = `${serviceId}/saveOrUpdate`
    return http.post(url, data)
  },
  one (data) {
    const url = `${serviceId}/one`
    return http.get(url, data)
  }

}
