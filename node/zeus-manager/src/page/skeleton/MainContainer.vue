<template>
  <el-container :style="styleObj">
    <el-header height="50px" style="background-color: #2D8AEB">
      <router-view name="header"></router-view>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <router-view name="aside"></router-view>
      </el-aside>
      <el-main>
        <router-view name="main"></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  export default {
    data() {
      const item = {
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄',
        styleObj: {}
      };
      return {
        tableData: Array(50).fill(item)
      }
    },
    created() {
      this.styleObj = {
        height: window.innerHeight + 'px',
      }
    },
    mounted() {

    },
    beforeDestroy() {
    },
    methods: {}
  }
</script>

<style scoped>

</style>
