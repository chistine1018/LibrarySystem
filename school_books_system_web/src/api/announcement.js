import http from '../utils/http'

/**
 *  api 前缀
 */
const serviceId = '/announcement'

export default {

  page (data) {
    const url = `${serviceId}/page`
    return http.get(url, data)
  },
  save (data) {
    const url = `${serviceId}/save`
    return http.post(url, data)
  },
  delete (data) {
    const url = `${serviceId}/delete`
    return http.get(url, data)
  }

}
