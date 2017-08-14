<template>
  <div style="position: absolute;
        z-index:1000;
        width:100%;
        height:100%;
        background: rgba(0, 0, 0, 0.5);
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        display: flex;
        justify-content: center;
        align-items: center;">
    <img ref="imageRef" class="item-img" border="0" :src="imgUrl"/>
  </div>
</template>

<script>
  export default {
    props:{
      imgUrl:{
        type:String,
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
    },
    created(){
    },
    mounted(){
    },
    destroyed(){
    },
    activated() {
    },
    deactivated() {
    },
    components:{},
  }
</script>

<style lang="less" rel="stylesheet/less" scoped>

</style>
