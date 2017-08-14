/**
 * Created by cheguo on 2017/1/5.
 */
import Vue from "vue";

var bus = new Vue();  //全局唯一

export default {
  post(eventId,...args){
    bus.$emit(eventId, ...args);
  },
  register(eventId,method){
    bus.$on(eventId,method);
  },
  unRegister(eventId){
    bus.$off(eventId);
  }
}


