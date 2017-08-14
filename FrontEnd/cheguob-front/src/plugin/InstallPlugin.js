/**
 * 统一配置自定义插件
 */
import Toast from "./toast/index";
import Indicator from "./indicator/index";
import Dialog from "./dialog/index";
import VueAwesomeSwiper from 'vue-awesome-swiper'

export default function(Vue) {
  Vue.use(Toast);
  Vue.use(Indicator);
  Vue.use(Dialog);
  Vue.use(VueAwesomeSwiper);
}
