import IndicatorView from './IndicatorView.vue'

export default function(Vue, defaultOptions = {}) {

  const CONSTRUCTOR = Vue.extend(IndicatorView);

  let progress = new CONSTRUCTOR();
  if (!progress.$el) {
    let vm = progress.$mount();
    document.querySelector('body').appendChild(vm.$el);
  }

  Vue.prototype.$indicator = progress;
}
