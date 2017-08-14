<template>
  <div>
    <div class="item" @click="clickAuth">
      <img class="item-img" :src="image"/>
      <div class="item-content">
        <span style="font-size: 14px;color: #262626">{{title}}</span>
        <div style="font-size: 12px;color: #999999;margin-top: 10px;line-height: 150%">{{content}}</div>
      </div>
      <span :class="clazzObject">{{statusStr}}</span>
    </div>
  </div>
</template>

<script>
  import Divider from "../../../components/Divider.vue";
  import StatuMixin from './StatusMixin';

  export default {
    props:{
      title:{
        type:String,
        required:true
      },
      content:{
        type:String,
        required:true
      },
      image:{
        type:String,
      },
      status:{  //0 未认证，1 认证中，2 已认证，3 认证失败
        type:Number,
        required:true
      },
    },
    data(){
      return {
      }
    },
    mixins: [StatuMixin],
    computed: {
    },
    methods:{
      clickAuth(){
        this.$emit("clickAuth");
      }
    },
    created(){
    },
    mounted(){
    },
    destroyed(){
    },
    components:{Divider},
  }
</script>

<style lang="less" rel="stylesheet/less" scoped>
  @import "../../../assets/css/common.less";
  @import "../../../assets/css/color.less";
  @import "../../../assets/css/dimen.less";

  .item{
    display: flex;
    flex-flow: row nowrap;
    align-items: center;
    padding: 15px;
    background: #fff
  }
  .item-img{
    width: 27px;
    height: 27px
  }
  .item-content{
    margin: 0 15px;
    flex: 1 0 0
  }
  .auth-no{
    display: flex;
    justify-content: center;
    align-items: center;
    width:50px;
    height: 30px;
    font-size: 13px;
    color: #fff;
    background: #1aa26a;
    border-radius: 3px;
  }

  .auth-ing{
    font-size: 13px;
    color: #f68a2b;
  }

  .status-success{
    font-size: 13px;
    color: #1aa26a;
  }

  .auth-fail{
    font-size: 13px;
    color: #ff5a37;
  }

</style>
