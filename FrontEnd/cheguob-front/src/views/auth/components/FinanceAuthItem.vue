<template>
    <div style="background: #fff">
      <Divider/>
      <div class="item" @click="clickItem">
        <div class="item-left">
          <div style="font-size: 14px;
                      color: #262626;">{{title}}</div>
          <div style="margin-top: 10px;
                    font-size: 12px;
                    color: #999999">{{content}}</div>
        </div>
        <div :class="clazzObject">{{statusStr}}</div>
      </div>
      <Divider/>
      <div v-if="!!btnStr" style="padding: 5px 10px">
        <span @click="clickAuth" class="btn">{{btnStr}}</span>
      </div>
      <Divider/>
    </div>
</template>

<script>
  import Divider from "../../../components/Divider.vue";
  import StatuMixin from './StatusMixin';

  export default {
    props:{
      title:{
        type:String,
        required:false
      },
      content:{
        type:String,
        required:false
      },
      status:{  //0:未认证 1:已认证 2:认证中3:认证失败
        type:Number,
        required:true
      }
    },
    data(){
      return {
      }
    },
    mixins: [StatuMixin],
    computed: {
      btnStr(){
        let str;
        switch (this.status){  //0 未认证，1 认证中，2 已认证，3 认证失败
          case 0:
            str="认证";
            break;
          case 3:
            str="重新认证";
            break;
          default:
            str='';
            break;
        }
        return str;
      },
      statusStr(){
        let str;
        switch (this.status){  //0 未认证，1 认证中，2 已认证，3 认证失败
          case 0:
            str="";
            break;
          case 1:
            str="认证中";
            break;
          case 2:
            str="已认证";
            break;
          case 3:
            str="认证失败";
            break;
          default:
            str='';
            break;
        }
        return str;
      }
    },
    methods:{
      clickItem(){
        console.log("clickItem")
        this.$emit("clickItem");
      },
      clickAuth(){
        console.log("clickAuth");
        this.$emit("clickAuth");
      }
    },
    created(){
    },
    mounted(){
    },
    destroyed(){
    },
    components:{Divider}
  }
</script>

<style lang="less" rel="stylesheet/less" scoped>
  .item{
    display: flex;
    flex-flow: row nowrap;
    align-items: center;
    padding: 15px;
  }
  .item-left{
    flex: 1 0 0;
  }
  .auth-no{
    font-size: 13px;
    color: #1aa26a;
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
  .btn{
    float: right;
    display: flex;
    justify-content: center;
    align-items: center;
    width:80px;
    height: 30px;
    font-size: 13px;
    color: #fff;
    background: #1aa26a;
    border-radius: 3px;
  }

</style>
