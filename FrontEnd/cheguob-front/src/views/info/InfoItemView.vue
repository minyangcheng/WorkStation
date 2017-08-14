<template>
  <div>
    <div class="item" style="display: flex;flex-flow: row nowrap;align-items: center;padding: 10px 0;" @click="onClickItem">
      <div style="flex: 1 0 0;margin-left: 15px">
        <div style="color: #222222;font-size: 16px;max-lines: 2;text-overflow:ellipsis;">{{value.title}}</div>
        <div style="margin-top: 5px;color: #999999;font-size: 12px;text-overflow:ellipsis;white-space: nowrap;">{{value.content}}</div>
      </div>
      <img style="width: 120px;height: 70px;background: #cccccc;margin-left: 10px;margin-right: 15px" :src="value.image"/>
    </div>
    <Divider/>
  </div>
</template>

<script>
  import Divider from "./../../components/Divider.vue";
  export default {
    props:{
      value:{
        type:Object,
        required:true
      }
    },
    data(){
      return {
      }
    },
    computed: {
    },
    methods:{
      onClickItem(){
        this.$emit('onClickItem',this.value);
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
  .item{
    background: #fff;
  }
  .item:active{
    background: #cccccc;
  }
</style>
