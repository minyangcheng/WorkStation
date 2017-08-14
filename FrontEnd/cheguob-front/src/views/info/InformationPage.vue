<template>
  <div class="page" style="display: flex;flex-flow: column nowrap">
    <Titlebar title="咨询" rightBtnTxt="上传" @clickRight="clickRight"></Titlebar>
    <TabsView :dataList="tabsArr" @onTabIndexChange="onTabIndexChange"></TabsView>
    <LoadMoreView style="flex: 1 0 0" ref="loadMoreRef" @onLoadMore="onLoadMore">
      <InfoItemView v-for="(item,index) in dataList" :key="index" :value="item" @onClickItem="onClickItem"></InfoItemView>
    </LoadMoreView>
  </div>
</template>

<script>
  import TabsView from '../../components/TabsView.vue';
  import LoadMoreView from './../../components/LoadMoreView.vue';
  import InfoItemView from './InfoItemView.vue';
  import Titlebar from './../../components/Titlebar.vue';
  export default {
    data(){
      return {
        tabsArr:['车国百度','新闻专栏','车主说'],
        dataList:[],
        nowPageIndex:0,
        type:0,
      }
    },
    computed: {
    },
    methods:{
      clickRight(){
        console.log("click right btn");
      },
      onTabIndexChange(index){
        console.log("tab index=%s",index);
//        this.type=index+1;
        this.nowPageIndex=0;
        this.fetchData();
      },
      onLoadMore(){
        this.fetchData();
      },
      fetchData(type){
        this.$http.post("/mobile/getSpiderInformationList.json",{type:this.type,page:this.nowPageIndex}).then(response=>{
          console.log(response);
          if(this.nowPageIndex==0){
            this.dataList=response.body;
          }else{
            this.dataList=this.dataList.concat(response.body);
          }
          this.nowPageIndex++;
          if(this.dataList&&this.dataList.length<15){
            this.$refs.loadMoreRef.setLoadMoreDone();
          }
        },response=>{
          console.log(response);
        });
      },
      onClickItem(value){
        console.log(JSON.stringify(value));
      }
    },
    created(){
    },
    mounted(){
      this.fetchData();
    },
    destroyed(){
    },
    components:{Titlebar,TabsView,LoadMoreView,InfoItemView},
  }
</script>

<style lang="less" rel="stylesheet/less" scoped>
  @import "./../../assets/css/common";
</style>
