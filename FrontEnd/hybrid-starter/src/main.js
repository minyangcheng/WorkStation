import Vue from 'vue'
import App from './App'
import router from './router'
import BridgeApi from './plugin/bridge-api'

Vue.config.productionTip = false

Vue.use(BridgeApi)

new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})
