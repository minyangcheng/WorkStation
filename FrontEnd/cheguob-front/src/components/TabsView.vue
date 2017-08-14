<template>
  <div>
    <Divider/>
    <div class="tabs">
      <div v-for="(item,index) in dataList" class="tab" @click="click(index)">
        <div class="tab-text" :class="{'tab-text-active':nowIndex==index}">{{item}}</div>
      </div>
    </div>
    <Divider/>
  </div>
</template>

<script>
  import Divider from "./Divider.vue";
  export default {
    props:{
      dataList:{
        type:Array,
        required:true,
      },
    },
    data(){
      return {
        nowIndex:0
      }
    },
    computed: {
    },
    methods:{
      click(index){
        if(this.nowIndex!=index){
          this.nowIndex=index;
          this.$emit("onTabIndexChange",this.nowIndex);
        }
      }
    },
    created(){
    },
    mounted(){
    },
    destroyed(){
    },
    components:{Divider},
  }
</script>

<style scoped>
  .tabs{
    display: flex;
    flex-flow: row nowrap;
    width: 100%;
    height: 44px;
    background: #fff;
    position: relative;
  }
  .tab{
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    flex: 1;
  }
  .tab-text{
    display: flex;
    justify-content: center;
    align-items: center;
    color: #808080;
    font-size: 15px;
    height: 100%;
    padding: 0 5px;
    border-radius: 1px;
  }
  .tab-text-active{
    border-bottom: #ff5a37 3px solid;
    border-spacing: 50px;
    color: #404040;
  }
</style>
