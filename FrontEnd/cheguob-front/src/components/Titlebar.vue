<template>
  <nav class="title-bar">
    <span class="title-bar-back-img" @click="goBack"></span>
    <span class="title-bar-back-text" @click="goBack">返回</span>
    <span class="title-bar-center">{{title}}</span>
    <span class="title-bar-right" @click="clickRight">{{rightBtnTxt}}</span>
  </nav>
</template>

<script>
  import Zeus from "./../utils/Zeus";
  export default {
    props:{
      title:{
        type:String,
        default:""
      },
      rightBtnTxt:{
        type:String,
        default:""
      }
    },
    data(){
      return {
      }
    },
    computed: {
    },
    methods:{
      clickRight(){
        this.$emit('clickRight');
      },
      goBack(){
        if(window.cgwapp){
          Zeus.goBack();
        }else{
          this.$router.go(-1);
        }
      }
    },
    created(){
    },
    mounted(){
    },
    destroyed(){
    },
    components:{},
  }
</script>

<style lang="less" rel="stylesheet/less" scoped>
  @import "./../assets/css/common";
  .title-bar{
    position: relative;
    height: 45px;
    padding: 0 10px;
    background: #f9f9f9;
  }
  .title-bar-back-img{
    position: relative;
    top: 50%;
    transform: translate(0,-50%);
    width: 14px;
    height: 14px;
    display: inline-block;
    background: url(./../assets/img/ic_back.png) center no-repeat;
    background-size: contain;
    float: left;
  }
  .title-bar-back-text{
    position: absolute;
    top: 50%;
    transform: translate(0,-50%);
    font-size: 15px;
    color: #ff5a37;
  }
  .title-bar-center{
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
    font-size: 17px;
    color: #404040;
  }
  .title-bar-right{
    float: right;
    height: 100%;
    margin-left: 8px;
    line-height: 45px;
    font-size: 15px;
    color: #ff5a37;
  }
</style>
