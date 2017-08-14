import EasyToast from './EasyToast.vue'

export default function(Vue, defaultOptions = {}) {

  const CONSTRUCTOR = Vue.extend(EasyToast);
  const CACHE = {};
  Object.assign(EasyToast.DEFAULT_OPT, defaultOptions);

  function toast(msg, options = {}) {
    options.message = msg;
    let toast = CACHE[options.id] || (CACHE[options.id] = new CONSTRUCTOR());
    if (!toast.$el) {
      let vm = toast.$mount();
      document.querySelector(options.parent || 'body').appendChild(vm.$el);
    }
    toast.queue.push(options);
  }

  Vue.prototype.$toast = toast;
}
