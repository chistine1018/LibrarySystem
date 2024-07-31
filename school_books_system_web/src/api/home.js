import http from '../utils/http'

/**
 *  api 前缀
 */
const serviceId = '/home'

export default {

  home () {
    const url = `${serviceId}`
    return http.get(url)
  }
}
