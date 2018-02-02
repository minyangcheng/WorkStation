import Vue from 'vue'
import App from './App'
import router from './router'
// import BridgeApi from './plugin/bridge-api'
import EurdaSrc from './assets/js/eruda.min'
import CommonUtil from './util/CommonUtil'
import Vuelidate from 'vuelidate'
import Hybrid from './hybrid/index'
import AllH5 from './hybrid/api/allh5'
import AllNative from './hybrid/api/allnative'

if(process.env.NODE_ENV==='development'||CommonUtil.isRunInPhone()){
  //在手机上显示出调试快捷
  window.eruda.init()
}

Vue.config.productionTip = false
// Vue.use(BridgeApi)
Vue.use(Vuelidate)

new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})
