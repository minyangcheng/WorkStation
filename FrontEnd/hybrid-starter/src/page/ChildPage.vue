<template>
  <div class="page">
    <h1>{{ msg }}</h1>
    <button @click="go">go</button>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        msg: 'ChildPage'
      }
    },
    methods: {
      go(){
        this.$bridge.postEvent('testLog',{name:"minych"})
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
