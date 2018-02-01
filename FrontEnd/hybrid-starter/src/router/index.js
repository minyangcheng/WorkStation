import Vue from "vue";
import Router from "vue-router";
import MainPage from "../page/MainPage.vue";
import ChildPage from "../page/ChildPage.vue";
import HybridPage from '../page/HybridPage.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path:"/",
      component:HybridPage,
    },
    {
      path: '/MainPage',
      component: MainPage
    },
    {
      path: '/ChildPage',
      component: ChildPage
    },
  ]
})
