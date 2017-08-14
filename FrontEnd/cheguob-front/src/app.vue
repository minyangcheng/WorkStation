<template>
  <transition name="fade">
    <!--<keep-alive>-->
      <router-view v-if="!loading"></router-view>
    <!--</keep-alive>-->
  </transition>
</template>
<script type="text/ecmascript-6">
  import Zeus from "./utils/Zeus";
  import Divider from "./components/Divider.vue";
  export default {
    data(){
      return {
        loading:true,
      }
    },
    created(){
      Zeus.setCanRefresh(false);
    },
    mounted(){
//      this.$indicator.show('1234567891');
      setTimeout(()=>{
        this.loading=false;
      },1000);
    },
    components:{Divider},
  }
</script>
<style lang="less" rel="stylesheet/less">
  @import "./assets/css/normalize";

</style>
