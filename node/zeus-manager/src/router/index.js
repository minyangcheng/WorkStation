import Vue from "vue";
import Router from "vue-router";
import MainContainer from '../page/skeleton/MainContainer';
import Aside from '../page/skeleton/Aside';
import Header from '../page/skeleton/Header';
import UpdatePage from '../page/UpdatePage';
import WelcomePage from '../page/WelcomePage';
import SettingPage from '../page/SettingPage';

Vue.use(Router);

let router = new Router({
  routes: [
    {
      path: "/",
      redirect: '/MainContainer',
    },
    {
      path: "/MainContainer",
      component: MainContainer,
      children: [
        {
          path: '',
          components: {
            header: Header,
            aside: Aside,
            main: WelcomePage
          }
        },
        {
          path: 'UpdatePage',
          components: {
            header: Header,
            aside: Aside,
            main: UpdatePage
          }
        },
        {
          path: 'SettingPage',
          components: {
            header: Header,
            aside: Aside,
            main: SettingPage
          }
        }
      ]
    }
  ]
});

router.beforeEach((to, from, next) => {
  next();
});

export default router;
