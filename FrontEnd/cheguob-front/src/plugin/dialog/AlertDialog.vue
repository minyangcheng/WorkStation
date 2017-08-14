<template>
  <transition name="fade">
    <div class="layer" v-if="showFlag">
      <div class="dialog">
        <div class="title">{{title}}</div>
        <div class="content">{{message}}</div>
        <div class="btns">
          <span class="btn-item" @click="clickCancel">{{computeCancelStr}}</span>
          <span class="btn-item" @click="clickOk">{{computeOkStr}}</span>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
  export default {
    data(){
      return {
        showFlag:false,
        title:"",
        message:"",
        cancelStr:"",
        cancelListener:null,
        okStr:"",
        okListener:null,
      }
    },
    computed: {
      computeCancelStr(){
        if(this.cancelStr){
          return this.cancelStr;
        }else{
          return "取消";
        }
      },
      computeOkStr(){
        if(this.okStr){
          return this.okStr;
        }else{
          return "确定";
        }
      }
    },
    methods:{
      show(title,message,cancelStr,cancelListener,okStr,okListener){
        if(!title||!message) {
          console.log("对话框title和title不能为空");
          return;
        }
        console.log(title,message,cancelStr,cancelListener,okStr,okListener);
        this.showFlag=true;
        this.title=title;
        this.message=message;
        this.cancelStr=cancelStr;
        this.cancelListener=cancelListener;
        this.okStr=okStr;
        this.okListener=okListener;
      },
      close(){
        this.showFlag=false;
      },
      clickCancel(){
        if(this.cancelListener){
          this.cancelListener(this);
        }else{
          this.close();
        }
      },
      clickOk(){
        if(this.okListener){
          this.okListener(this);
        }else{
          this.close();
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
  @import "./../../assets/css/common";

  .layer{
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    z-index: 999;
  }

  .dialog{
    .center;
    width: 80%;
    padding: 10px;
    background: #fff;
    border-radius: 3px;
  }

  .title{
    font-size: 14px;
    color: #000;
    font-weight:bold;
  }

  .content{
    margin-top: 10px;
    margin-bottom: 5px;
    font-size: 13px;
    color: #000;
    max-height: 60%;
    overflow: auto;
  }

  .btns{
    float: right;
  }

  .btn-item{
    padding: 5px;
    font-size: 12px;
    color: #007aff;
    background: #fff;
    &:active{
      background: #d9d9d9;
    }
  }

  .fade-enter-active {
    animation: in .3s;
  }
  .fade-leave-active {
    animation: out .3s;
  }
  @keyframes in
  {
    from {opacity: 0;}
    to {opacity: 1;}
  }
  @keyframes out
  {
    from {opacity: 1;}
    to {opacity: 0;}
  }

</style>
