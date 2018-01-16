import Bridge from './bridge'

export default function (Vue, defaultOptions = {}) {

  /**
   *
   * @param message
   */
  Bridge.nativeLog = function (payload) {
    if (payload) {
      Bridge.send('nativeLog', payload)
    }
  }

  /**
   *
   * @param message
   */
  Bridge.toast = function (message) {
    if (message) {
      Bridge.send('toast', {data: message})
    }
  }

  /**
   * this.$bridge.openActivity('http://10.10.13.117:8080/#/ChildPage');
   * this.$bridge.openActivity('com.min.hybrid.sample.NativeTwoActivity',{name:'minyangcheng|str',age:'12|int'});
   *
   * @param destination
   * @param payload
   */
  Bridge.openActivity = function (destination,payload={}) {
    if(destination){
      payload=Object.assign(payload, {destination})
      Bridge.send('openActivity', payload)
    }
  }

  /**
   * this.$bridge.on('testLog',function (payload) {}) 监听
   * this.$bridge.postEvent('testLog',{name:"minych"}) 发送通知
   *
   * @param type
   * @param payload
   */
  Bridge.postEvent = function (type,payload={}) {
    if(type){
      payload=Object.assign(payload, {type})
      Bridge.send('postEvent', payload)
    }
  }

  Vue.prototype.$bridge = Bridge
}
