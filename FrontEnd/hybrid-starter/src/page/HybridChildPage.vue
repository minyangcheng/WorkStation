<template>
  <div class="page">
    <button @click="toast">toast</button>
    <button @click="alert">alert</button>
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
    created(){
      quick.navigator.setTitle({
        title: 'child',
        success: function (result) {
        },
        error: function (err) {
        }
      });
    },
    mounted(){
    },
    beforeDestroy(){
    },
    mixins: [LifeCycleLog],
    methods: {
      toast(){
        quick.ui.toast('sd#ddd测试');
      },
      alert(){
        quick.page.postEvent({
          type:'main',
          data:{'name':'min','age':123}
        });
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
