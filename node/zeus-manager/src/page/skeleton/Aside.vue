<template>
  <el-menu style="height: 100%">
    <el-menu-item v-for="(item,index) in items" :index="index+''" :key="index" @click="clickItem(item,index)">
      {{item.title}}
    </el-menu-item>
  </el-menu>
</template>

<script>
  export default {
    data() {
      return {
        items: [{title: '欢迎', path: '/MainContainer'},
          {title: '上传更新包', path: '/MainContainer/UpdatePage'},
        ],
        initPos:0
      }
    },
    created(){
      this.items.forEach((value ,index)=>{
        if(value.path==this.$route.path){
          this.initPos=index;
        }
      });
      setTimeout(()=> {
        $($("[role=menuitem]").get(this.initPos)).addClass("is-active");
      }, 0);
    },
    methods: {
      clickItem(item, index) {
        if(this.initPos>=0){
          console.log(this.initPos)
          $($("[role=menuitem]").get(this.initPos)).removeClass("is-active");
          this.initPos=-1;
        }
        this.$router.replace(item.path);
      }
    }
  }
</script>

<style scoped>

</style>
