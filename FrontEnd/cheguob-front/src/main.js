import 'babel-polyfill';
import Vue from "vue";
import VueRouter from "vue-router";
import routes from './routes.js';
import Vuex from 'vuex';
import storeOption from './vuex/store';
import App from "./app.vue";
import Zeus from "./utils/Zeus";
import installPlugin from "./plugin/InstallPlugin";
import VueResource from "vue-resource";
import setHttpInterceptor from "./net/Interceptor";
import screenAdapter from "./utils/screenAdapter";
import MintUI from 'mint-ui'
import 'mint-ui/lib/style.css'
import PropertyUtil from './utils/PrototypeUtil'

//配置mint
Vue.use(MintUI);

//配置路由
Vue.use(VueRouter);
var router = new VueRouter({
  // mode: 'history',
  scrollBehavior: () => ({y: 0}),
  routes
});
router.beforeEach((to, from, next) => {
  //根据路由中的meta动态显示title
  if(to.meta&&to.meta.title){
    Zeus.setToolbarTitle(to.meta.title);
  }else{
    Zeus.setToolbarTitle(null);
  }
  //根据路由中的meta动态显示rightBtnName
  if(to.meta&&to.meta.rightBtnName){
    Zeus.showRightButton(to.meta.rightBtnName,to.meta.rightBtnFun);
  }else{
    Zeus.showRightButton('','');
  }
  next();
});

// 配置vuex
Vue.use(Vuex);
const store = new Vuex.Store(storeOption);

//配置vue-resource
Vue.use(VueResource);
setHttpInterceptor(Vue);

//配置自定义插件
installPlugin(Vue);

var app = new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
});

screenAdapter();

