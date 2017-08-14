/**
 * Created by cheguo on 2017/4/10.
 */
import Timeago from 'timeago.js';

export default {

  /**
   * 判断空值
   * @param value
   * @returns {boolean}
   */
  isEmpty(value){
    if(value==undefined||value==null) return true;
    if(typeof value=="string" && value.trim()=="") return true;
    return false;
  },

  /**
   * 修复android webview和swiperefreshlayout会出现下拉直接回调到刷新空间上去
   * @param dom
   */
  hackAndroidRefreshBug(dom){
    var clientType=window.cgwapp.getClientType();
    if(clientType==1){
    dom.addEventListener("scroll",function (e) {
      console.log("contentId=%s",e.target);
      if($(dom).scrollTop()==0){
        console.log("set refresh true");
        window.cgwapp.setCanRefresh(true);
      }else{
        console.log("set refresh false");
        window.cgwapp.setCanRefresh(false);
      }
    });
    }
  },

  /**
   * 处理网络请求错误
   * @param vm
   * @param response
   * @returns {*|string|number|Number}
   */
  handlerError(vm,response){
    vm.$toast(response.body.message);
    return response.code;
  },

  /**
   *   对Date的扩展，将 Date 转化为指定格式的String
   *   月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
   *   年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
   *   例子：
   *   (new Date()).Format('yyyy-MM-dd hh:mm:ss.S') ==> 2006-07-02 08:09:04.423
   *   (new Date()).Format('yyyy-M-d h:m:s.S')      ==> 2006-7-2 8:9:4.18
   */
  formatDate(date, fmt){
    var o = {
      'M+': date.getMonth() + 1, // 月份
      'd+': date.getDate(), // 日
      'h+': date.getHours(), // 小时
      'm+': date.getMinutes(), // 分
      's+': date.getSeconds(), // 秒
      'q+': Math.floor((date.getMonth() + 3) / 3), // 季度
      'S': date.getMilliseconds() // 毫秒
    };
    if (/(y+)/.test(fmt)) {
      fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
      if (new RegExp('(' + k + ')').test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)));
      }
    }
    return fmt;
  },

  /**
   * 调用Timeago库显示到现在的时间
   */
  formatTimeFriendly(time){
    var str = '';
    if (time !== null && time !== '') {
      let timeagoInstance = new Timeago();
      str = timeagoInstance.format(time, 'zh_CN');
    }
    return str;
  },

  /**
   * 格式化日期或时间
   * @param {string} time 需要格式化的时间
   * @param {bool} friendly 是否是fromNow
   */
  getLastTimeStr(time, friendly){
    if (friendly) {
      return this.formatTimeFriendly(time);
    } else {
      return this.formatDate(new Date(time), 'yyyy-MM-dd hh:mm');
    }
  },

  /**
   * 判断类型
   * eg: is('String', 'test');
   * @param type
   * @param obj
   * @returns {boolean}
   */
  is(type, obj) {
    var clas = Object.prototype.toString.call(obj).slice(8, -1);
    return obj !== undefined && obj !== null && clas === type;
  },

  /**
   * 防抖函数
   * @param idle
   * @param action
   * @returns {Function}
   */
  debounce(idle, action){
    var last;
    return function(){
      var ctx = this;
      var args = arguments;
      clearTimeout(last);
      last = setTimeout(function(){
        action.apply(ctx, args);
      }, idle)
    }
  },

  /**
   * 节流函数
   * @param delay
   * @param action
   * @returns {Function}
   */
  throttle(delay, action){
    var last = 0;
    return function(){
      var curr = new Date();
      if (curr - last > delay){
        action.apply(this, arguments);
        last = curr;
      }
    }
  },

}
