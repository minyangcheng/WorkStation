<template>
  <div class="page">
    <h1>{{ msg }}</h1>
    <button @click="go">go</button>
  </div>
</template>

<script>
  import LifeCycleLog from './../mixins/mixin'
  export default {
    data () {
      return {
        msg: 'MainPage'
      }
    },
    mounted(){
      this.$bridge.on("getPageDataInfo", function (payload) {
        alert(44);
      })
    },
    beforeDestroy(){
      this.$bridge.off('getPageDataInfo');
    },
    mixins: [LifeCycleLog],
    methods: {
      go(){
        this.$bridge.toast('nihaoma?toast');
      }
    }
  }
</script>

<style scoped>
  .page {
    display: flex;
    flex-flow: column nowrap;
    align-items: center;
    width: 100%;
    height: 100%;
    background: #ffccc8;
  }

  button {
    width: 90%;
    height: 30px;
    margin-top: 10px;
  }
</style>
