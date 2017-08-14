<template>
  <div class="head-info">
    {{title}}
  </div>
</template>

<script>
export default {
  props:{
    title:{
      type:String,
      required: true,
    }
  }
}
</script>

<style lang="less" rel="stylesheet/less" scoped>
  @import "../assets/css/color.less";
  .head-info{
    display: flex;
    align-items: center;
    padding: 0 15px;
    height: 30px;
    background: #f2f2f2;
    font-size: 13px;
    color: #999999;
  }
</style>
