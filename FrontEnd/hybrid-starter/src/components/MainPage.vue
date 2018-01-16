<template>
  <div class="page">
    <h1>{{ msg }}</h1>
    <button @click="toast">toast</button>
    <button @click="nativeLog">nativeLog</button>
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
    mixins: [LifeCycleLog],
    created(){
      this.$bridge.on('testLog', payload => {
        this.$bridge.toast('this is event from other hybrid activity')
      });
    },
    methods: {
      toast(){
        this.$bridge.toast('nihao')
      },
      nativeLog(){
        this.$bridge.nativeLog({name:'minyab'});
      },
      go(){
        this.$bridge.openActivity('http://10.10.13.117:8080/#/ChildPage');
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
