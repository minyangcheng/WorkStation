import Vue from "vue";
import Router from "vue-router";
import MainPage from "../components/MainPage.vue";
import ChildPage from "../components/ChildPage.vue";

Vue.use(Router)

export default new Router({
  routes: [
    {
      path:"/",
      component:MainPage,
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
