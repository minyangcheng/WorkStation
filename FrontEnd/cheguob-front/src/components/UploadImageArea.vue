<template>
  <div class="grid">
    <UploadImageItem v-for="(item, index) in dataList" :index="index" key="index" :item="item" :enable="enable"></UploadImageItem>
  </div>
</template>

<script>
  import Zeus from "../utils/Zeus";
  import UploadImageItem from "./UploadImageItem.vue";

  export default {
    props:{
      dataList:{
        type:Array,
        required:true
      },
      enable:{
        type:Boolean,
        default:true
      },
    },
    data(){
      return {
      }
    },
    computed: {
    },
    methods:{
      getReuslt(){
        return this.dataList;
      },
      check(){
        let item;
        for(let i=0;i<this.dataList.length;i++){
            item=this.dataList[i];
            if(!item.uploadUrl){
              return item.title+"未上传";
            }
        }
        return null;
      }
    },
    created(){
    },
    mounted(){
    },
    destroyed(){
    },
    components:{UploadImageItem},
  }

</script>

<style lang="less" rel="stylesheet/less" scoped>

  .grid{
    display: flex;
    flex-flow: row wrap;
    padding: 8px;
  }

</style>
