import PersonalAuthPage from "./views/auth/PersonalAuthPage.vue";
import NotFoundPage from "./views/NotFoundPage.vue";
import InfoAuthPage from "./views/auth/InfoAuthPage";
import FinanceAuthListPage from "./views/auth/RefactorFinanceAuthListPage.vue";
import FinanceAuthApplyPage from "./views/auth/FinanceAuthApplyPage.vue";
import FinanceAuthInfoPage from "./views/auth/FinanceAuthInfoPage.vue";
import MainPage from "./views/MainPage.vue";
import InformationPage from "./views/info/InformationPage.vue";

var routes=[
  {
    path:"/",
    component:InfoAuthPage,
  },
  {
    path:"/MainPage",
    component:MainPage,
    meta:{title:'测试'}
  },
  {
    path:"/InformationPage",
    component:InformationPage,
    meta:{title:'咨询'}
  },
  {
    path:"/InfoAuthPage",
    component:InfoAuthPage,
    meta:{title:'信息认证'}
  },
  {
    path:"/PersonalAuthPage",
    component:PersonalAuthPage,
    meta:{title:'个人认证',rightBtnName:'认证',rightBtnFun:'PersonalAuthPage_Fun'}
  },
  {
    path:"/FinanceAuthListPage",
    component:FinanceAuthListPage,
    meta:{title:'高级认证'}
  },
  {
    path:"/FinanceAuthApplyPage",
    component:FinanceAuthApplyPage,
    meta:{title:'高级认证'}
  },
  {
    path:"/FinanceAuthInfoPage",
    component:FinanceAuthInfoPage,
    meta:{title:'高级认证'}
  },
  { path: '*',
    component: NotFoundPage
  }
];

export default routes;
