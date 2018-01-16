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
    mixins: [LifeCycleLog],
    created(){
      var self=this;
      this.$bridge.on('testLog', payload => {
        console.log(payload.name)
        self.$bridge.nativeLog({name:'minya',age:12})
      });
    },
    methods: {
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
