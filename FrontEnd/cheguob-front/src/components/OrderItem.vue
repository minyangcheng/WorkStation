<template>
  <div class="card" @click="onItemClick" style="background-color: #007aff">
        {{msg}}
  </div>
</template>

<script>
export default {
  props:{
        nowIndex:{
        type:Number,
        default:0
      }
  },
  computed:{
      msg(){
          return "这里是第"+this.nowIndex+"个card，下拉刷新会出现第"+(this.nowIndex+1)+"个card";
      }
  },
  methods:{
      onItemClick(){
          this.$emit("onItemClick");
      }
  }
}
</script>

<style scoped>
  .card{
    background: #ffffff;
    height: 80px;
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 5px;
  }
</style>
