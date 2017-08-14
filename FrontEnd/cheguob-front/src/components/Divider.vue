<template>
  <div>
    <div class="line" v-if="!big"></div>
    <div v-if="big">
      <div class="line"></div>
      <div class="line" style="margin-top: 8px"></div>
    </div>
  </div>
</template>

<script>
export default {
  props:{
    big:{
      type:Boolean,
      default:false
    }
  },
}
</script>

<style lang="less" rel="stylesheet/less" scoped>
  @import "../assets/css/color.less";
  @import "../assets/css/dimen.less";
  .line{
    height: 1px;
    width: 100%;
    background-color: @dividerColor;
    transform:scaleY(0.5);
    transform-origin:0 0;
  }
</style>
