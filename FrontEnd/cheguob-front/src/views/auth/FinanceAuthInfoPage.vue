/**
金融认证 信息
*/
<template>
  <div class="page" style="position: relative">
    <Divider big/>
    <div style="padding: 15px;background: #fff">
      <div style="font-size: 14px;color: #333333">{{$route.query.financialinfo.financialname}}</div>
      <div style="font-size: 12px;color: #666666;margin-top: 15px">
        {{$route.query.financialinfo.description}}
      </div>
    </div>
    <Divider big/>
    <div class="item">
      <span class="left">申请状态：</span>
      <span class="right" style="color: #ff702f">{{statusStr}}</span>
    </div>
    <Divider/>
    <div class="item">
      <span class="left">咨询电话：</span>
      <span class="right"><a :href="telUrl">{{$route.query.financialinfo.tel}}</a></span>
    </div>
    <Divider/>
  </div>
</template>

<script type="text/ecmascript-6">
  import Titlebar from "./../../components/Titlebar.vue";
  import Divider from "./../../components/Divider.vue";
  import StatuMixin from './components/StatusMixin';

  export default {
    props: {
      title: {
        type: String,
      }
    },
    data(){
      return {
        status: this.$route.query.financialinfo.verifystatus,
      }
    },
    mixins: [StatuMixin],
    computed: {
      telUrl(){
        return 'tel:'+this.$route.query.financialinfo.tel;
      }
    },
    methods: {},
    created(){
    },
    mounted(){
    },
    destroyed(){
    },
    components: {Titlebar, Divider},
  }
</script>

<style lang="less" rel="stylesheet/less" scoped>
  @import "../../assets/css/common.less";
  @import "../../assets/css/dimen.less";
  @import "../../assets/css/color.less";

  .item {
    padding: 8px 15px;
    background: #fff;
  }

  .left {
    font-size: 14px;
    color: #333333
  }

  .right {
    margin-left: 25px;
    font-size: 13px;
    color: #333333
  }
</style>
