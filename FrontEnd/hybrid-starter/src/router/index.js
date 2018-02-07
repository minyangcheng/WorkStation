import Vue from "vue";
import Router from "vue-router";
import MainPage from "../page/MainPage.vue";
import ChildPage from "../page/ChildPage.vue";
import HybridMainPage from '../page/HybridMainPage.vue'
import HybridChildPage from '../page/HybridChildPage.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path:"/",
      component:HybridMainPage,
    },
    {
      path:"/HybridMainPage",
      component:HybridMainPage,
    },
    {
      path:"/HybridChildPage",
      component:HybridChildPage,
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
