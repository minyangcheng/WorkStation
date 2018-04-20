<template>
  <div style="width: 100%;height: 100%;position: relative;">
    <span class="user-name">宙斯</span>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        msg: ''
      }
    },
    methods: {}
  }
</script>

<style lang="scss" scoped>
  @import "./../../assets/scss/common";

  .user-name{
    margin-right: 15px;
    color: #ffffff;
    font-size: 20px;
    line-height: 50px;
    @include horizontal-center;
  }

</style>
