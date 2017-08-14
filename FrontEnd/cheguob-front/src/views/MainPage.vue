<template>
  <div class="page" style="overflow: scroll">
    <swiper style="width:100%;height: 250px;background: #ffffff" :options="swiperOption">
      <swiper-slide>Slide 1</swiper-slide>
      <swiper-slide>Slide 2</swiper-slide>
      <swiper-slide>Slide 3</swiper-slide>
      <swiper-slide>Slide 4</swiper-slide>
      <swiper-slide>Slide 5</swiper-slide>
      <div class="swiper-pagination" slot="pagination"></div>
    </swiper>
    <button @click="toast">toast</button>
    <button @click="http">http</button>
    <button @click="indicator">indicator</button>
    <button @click="alertDialog">alert_dialog</button>
    <button @click="changeMessage">{{formatMsg}}</button>
    <button @click="promise">promise</button>
    <button @click="openPicker">openPicker</button>
    <button @click="test">test</button>
    <a href="tel:0755-10086">打电话给:0755-10086</a>
    <a :href="testUrl">test</a>
    <mt-datetime-picker
      ref="picker"
      type="time"
      v-model="pickerValue">
    </mt-datetime-picker>
  </div>
</template>

<script>
  import CommonUtil from "../utils/CommonUtil";
  import IndicatorView from "./../plugin/indicator/IndicatorView.vue";
  import { mapState } from 'vuex';
  import {mapGetters} from 'vuex';
  import { swiper, swiperSlide } from 'vue-awesome-swiper'
  import { DatetimePicker } from 'mint-ui';
  import md5 from 'js-md5';
  let Base64 = require('js-base64').Base64;

  export default {
    data(){
      return {
        swiperOption: {
          grabCursor : true,
          setWrapperSize :true,
          autoHeight: true,
          autoplay: 3000,
          pagination: '.swiper-pagination',
          paginationClickable: true
        },
        pickerValue:null,
        debounce:null,
        throttle:null,
        testUrl:'',
      }
    },
    computed: {
      count () {
        return 0;
      },
      ...mapState([
        'message',
      ]),
      ...mapGetters([
        'formatMsg',
      ])
    },
    methods:{
      toast(){
        this.$toast("nihaoma");
        console.log(process.env);
      },
      http(){
        this.$http.post("/dealer/getCompanyPersonInfo.json",{companyId:"18758032032",userpwd:"123456a"}).then(response => {
          console.log("success");
          console.log(response.body);
        }, response => {
          console.log("fail");
          console.log(response.body);
          CommonUtil.handlerError(this,response);
        });
      },
      indicator(){
        this.$indicator.show();
        setTimeout(()=>this.$indicator.close(),3000);
      },
      alertDialog(){
        this.$dialog.show("提示","自定义对话框内容","",(vm)=>{
          console.log(vm.message);
          vm.close();
        });
      },
      changeMessage(){
        this.$store.commit('message',"i am fine.");
      },
      promise(){
        let v=1;
        let task1=function (resolve,reject) {
          console.log("taks1 is running");
          if(v==1){
            resolve("success from task1");
          }else{
            reject("fail from task1");
          }
        };
        let task2=function (resolve,reject) {
          console.log("taks2 is running");
          if(v==1){
            resolve("success from taks2");
          }else{
            reject("fail from taks2");
          }
        };
        new Promise(task1).then(function (val) {
          return new Promise(task2);
        }).then(function (val) {
          console.log(val);
        },function (val) {
          console.log(val);
        })
      },
      openPicker() {
        this.$refs.picker.open();
      },
      test(){
        console.log("test");
        this.throttle("__");
        console.log("md5-->"+md5('闵羊城'));
        console.log('Base64-->'+Base64.encode('闵羊城'));
        let a="ni hao ma {0} , age {1}"
        console.log(a.format('minych',12))
      }
    },
    created(){
      let fun1=function (value) {
        console.log("fun1"+value);
      };
      this.debounce=CommonUtil.debounce(3000,fun1);
      this.throttle=CommonUtil.throttle(3000,fun1);
    },
    mounted(){
    },
    destroyed(){
    },
    components:{IndicatorView,[DatetimePicker.name]:DatetimePicker},
    beforeRouteEnter (to, from, next) {
      console.log("beforeRouteEnter");
      next();
    },
    beforeRouteLeave (to, from, next) {
      console.log("beforeRouteLeave");
      next();
    }
  }
</script>

<style lang="less" rel="stylesheet/less" scoped>
  @import "./../assets/css/common";
  button{
    display: block;
    width: calc(~"100% - 16px");
    height: 30px;
    margin: 8px;
  }
</style>
