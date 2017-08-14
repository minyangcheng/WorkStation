<template>
  <div class="item-container" @click="uploadImage">
    <img ref="imageRef" class="item-img" border="0" :style="{backgroundImage: 'url(' + imgUrl + ')'}"/>
    <span class="item-title">{{item.title}}</span>
    <ImagePreview v-if="showPreviewFlag" :imgUrl="imgUrl"></ImagePreview>
  </div>
</template>

<script>
  import logoSrc from '../assets/img/img_idcard_front.png'
  import Zeus from "../utils/Zeus";
  import ImagePreview from './../components/ImagePreview.vue';

  export default {
    props:{
      index:{
        type:Number,
      },
      item:{
        type:Object,
        required:true
      },
      enable:{
        type:Boolean,
        default:true
      },
    },
    data(){
      return {
        showPreviewFlag:false,
      }
    },
    computed: {
      imgUrl() {
        if(!(this.item.uploadUrl)){
          return this.item.holderImg;
        }else{
          return this.item.uploadUrl;
        }
      },
    },
    methods:{
      uploadImage(){
        if(this.enable){
          let self=this;
          window.UploadImageItem=function (result) {
            result=JSON.parse(result);
            self.item.uploadUrl=result.thumbnailserver+result.path;
          };
          Zeus.takePicture("UploadImageItem");
        }else{
//          this.showPreviewFlag=true;
          let url=this.imgUrl;
          if(url.startsWith("http:")){
            Zeus.showPictures([url],0);
          }
        }
      },
    },
    created(){
    },
    mounted(){
    },
    destroyed(){
      window.UploadImageItem=null;
    },
    components:{ImagePreview},
  }
</script>

<style lang="less" rel="stylesheet/less" scoped>
  @import "../assets/css/color.less";
  @import "../assets/css/dimen.less";
  @import "../assets/css/common.less";

  .item-container{
    flex: 1;
    padding: 4px;
  }
  .item-img{
    width: 100%;
    height: 125px;
    border-width: 0;
    outline-width: 0;
    background-repeat:no-repeat;
    background-position:center;
    background-size: cover;
  }
  .item-title{
    display: inline-block;
    width: 100%;
    text-align: center;
    font-size: @textPrimarySize;
    margin-top: 4px;
    color: #222;
    .single-line
  }
</style>
