// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import VueCookie from 'vue-cookie'
Vue.use(VueCookie) // 掛載在全局了
Vue.use(ElementUI) // 掛載在全局了

Vue.config.productionTip = false

// 全局判斷，當前登錄人角色是否在傳入的參數中
Vue.prototype.$roleRule = function (roles) {
  // 方法邏輯
  // 獲取當前登錄人角色
  let userRole = JSON.parse(VueCookie.get('authInfo')).role
  for (let i = 0; i < roles.length; i++) {
    // 和傳入參數集合的角色一一對比
    if (roles[i] === userRole) {
      // 存在，則表示可以訪問，返回 true
      return true
    }
  }
  // 當前登錄人角色，不在可訪問角色集合中，那就返回 false
  return false
}

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
