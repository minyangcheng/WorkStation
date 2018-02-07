<template>
  <div class="page">
    <button @click="toast">toast</button>
    <button @click="alert">alert</button>
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
    created(){
      console.log(this.$router)
      quick.navigator.setTitle({
        title: 'main',
        success: function(result) {},
        error: function(err) {}
      });

      quick.navigator.setRightBtn({
        isShow: 1,
        text: 'main',
        which: 0,
        success: function(result) {
          console.log(JSON.stringify(result));
        },
        error: function(error) {}
      });
    },
    mounted(){
      quick.on(this.$router.currentRoute.fullPath,'main',(data)=>{
        console.log(data)
      });
    },
    beforeDestroy(){
    },
    mixins: [LifeCycleLog],
    methods: {
      toast(){
        quick.ui.toast({
          message: "信息",
          success: function(result) {
            console.log(result)
          },
          error: function(err) {
            console.log(err)
          }
        });
      },
      alert(){
        quick.ui.alert({
          title: "标题",
          message: "信息",
          buttonName: "确定",
          cancelable: 1,
          success: function(result) {
            // 点击 alert的按钮后回调
          },
          error: function(err) {
            console.log(err)
          }
        });
      },
      go(){
        this.$router.push({'path':'/HybridChildPage'})
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
