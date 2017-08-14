import AlertDialog from './AlertDialog.vue'

export default function(Vue, defaultOptions = {}) {

  const CONSTRUCTOR = Vue.extend(AlertDialog);
  let dialog = new CONSTRUCTOR();
  if (!dialog.$el) {
    let vm = dialog.$mount();
    document.querySelector('body').appendChild(vm.$el);
  }

  Vue.prototype.$dialog = dialog;
}
