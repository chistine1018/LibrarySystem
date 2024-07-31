import http from '../utils/http'

/**
 *  api 前缀
 */
const serviceId = '/user'

export default {

  register (data) {
    const url = `${serviceId}/register`
    return http.post(url, data)
  },

  update (data) {
    const url = `${serviceId}/update`
    return http.post(url, data)
  },

  page (data) {
    const url = `${serviceId}/page`
    return http.get(url, data)
  },

  delete (data) {
    const url = `${serviceId}/delete`
    return http.get(url, data)
  }
}
