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
        this.$bridge.openPage('/ChildPage',this);
//        this.$bridge.openActivity('file:///android_asset/index.html#/ChildPage');
//        this.$bridge.openActivity('file:////data/user/0/com.min.hybrid.sample/files/webapp/index.html#/ChildPage?name=min')
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
