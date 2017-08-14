<template>
  <div id="loadMoreView" style="overflow: auto;">
    <slot></slot>
    <div class="footer" :class="{ hiddenFooter : hiddenFooter }">
      飞速加载中...
    </div>
  </div>
</template>

<script>
export default {
  data(){
      return {
          isLoading:false,
          hiddenFooter:true,
      };
  },
  methods:{
    setLoadMoreDone(){
        this.isLoading=false;
        this.hiddenFooter=true;
    }
  },
  mounted(){
    var el = $(this.$el);
    el.scroll(function () {
      let height = parseFloat(el.height());
      let scrollTop = parseFloat(el.scrollTop());
      let totalHeight = height + scrollTop;
//      console.log("height="+height+",scrollTop="+scrollTop+",totalHeight="+totalHeight+",scrollHeight="+el.get(0).scrollHeight);
      if (el.get(0).scrollHeight - totalHeight <= 20) {
        if(this.isLoading){
          return;
        }
        this.isLoading=true;
        this.hiddenFooter=false;
        this.$emit("onLoadMore");
      }
    }.bind(this));
  }
}
</script>

<style scoped>
  .hiddenFooter{
    display: none !important;
  }
  .footer{
    display: flex;
    justify-content: center;
    align-items: center;
    height: 40px;
    color: #ff5e36;
  }
</style>
