/*global Vue*/

/* weex initialized here, please do not move this line */
const router = require('./router');
const App = require('@/index.vue');
/* eslint-disable no-new */
new Vue(Vue.util.extend({el: '#root', router}, App));

var modal = weex.requireModule('modal')
modal.alert({
    message: JSON.stringify(weex.config)+1111,
    duration: 0.3
}, function (value) {
    console.log('alert callback', value)
})

router.push('/');

