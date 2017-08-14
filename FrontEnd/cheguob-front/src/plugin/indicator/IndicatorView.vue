<template>
  <transition name="fade">
    <div class="layer" v-show="showFlag">
      <div class="indicator">
        <img src="./img_loading_progress.png"/>
        <span v-if="showTitleFlag">{{title}}</span>
      </div>
    </div>
  </transition>
</template>

<script>
  export default {
    data(){
      return {
        title:"",
        showFlag:false,
      }
    },
    computed: {
      showTitleFlag() {
        if(this.title){
          return true;
        }else{
          return false;
        }
      },
    },
    methods:{
      show(title){
        this.showFlag=true;
        this.title=title;
      },
      close(){
        this.showFlag=false;
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
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0);
  }
  .indicator{
    background: rgba(0, 0, 0, 0.8);
    border-radius: 6px;
    padding: 5px 8px;
    .center;
  }
  .indicator img{
    display: block;
    width: 50px;
    height: 50px;
    animation: looper 5s linear infinite;
    /*position: relative;*/
    /*left: 50%;*/
    /*transform: translate(-50%,0);*/
  }
  @keyframes looper
  {
    from {transform: rotate(0deg);}
    to {transform: rotate(360deg);}
  }
  .indicator span{
    display: block;
    font-size: 14px;
    color: #fff;
    .single-line;
    text-align: center;
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
