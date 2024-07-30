import http from '../utils/http'

/**
 *  api 前缀
 */
const serviceId = '/superAdmin'

export default {

  page (data) {
    const url = `${serviceId}/page`
    return http.get(url, data)
  },

  saveOrUpdate (data) {
    const url = `${serviceId}/saveOrUpdate`
    return http.post(url, data)
  },

  delete (data) {
    const url = `${serviceId}/delete`
    return http.get(url, data)
  }

}
