import Vue from 'vue';
import App from './App';
import router from './router';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './plugin/HttpClientPlugin'
import HttpClientPlugin from "./plugin/HttpClientPlugin";

Vue.config.productionTip = false;
Vue.use(ElementUI);

Vue.use(HttpClientPlugin);

new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: {App}
});

