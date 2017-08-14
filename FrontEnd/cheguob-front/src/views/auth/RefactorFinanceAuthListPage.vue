/**
金融认证 列表(新需求)
*/
<template>
  <div class="page">
    <div v-for="(item,index) in financeDataList" :key="index">
      <FinanceAuthItem :title="item.financialname"
                       :content="item.description"
                       :status="item.verifystatus"
                       @clickItem="clickItem(item)"
                       @clickAuth="clickAuth(item)"
                       style="margin-top: 8px"></FinanceAuthItem>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  import Titlebar from "./../../components/Titlebar.vue";
  import FinanceAuthItem from "./components/FinanceAuthItem.vue";
  import Divider from "./../../components/Divider.vue";
  import Zeus from './../../utils/Zeus';
  import CommonUtil from './../../utils/CommonUtil';

  export default {
    props: {
      title: {
        type: String,
      }
    },
    data(){
      return {
        financeDataList: [],
      }
    },
    computed: {
    },
    methods: {
      clickItem(item){
      },
      clickAuth(item){
        this.clickApply(item);
      },
      clickApply(item){
        let financialinfo = {
          verifystatus: item.verifystatus,
          userid: Zeus.getUserid(),
          companyid: Zeus.getCompanyid(),
          financialid: item.financialid,
          applyid: item.applyid,
        };
        this.$indicator.show();
        this.$http.post("/dealer/authfinancialcompany.json", {financialinfo: JSON.stringify(financialinfo)}).then(response => {
          this.$indicator.close();
          console.log(response);
          this.$toast(response.message);
          item.verifystatus=1;
        }, response => {
          CommonUtil.handlerError(this, response);
          this.$indicator.close();
        });
      },
      getData(){
        //获取认证金融机构

        this.$indicator.show();
        this.$http.post("/dealer/getfinancialinstitution.json", {companyid:Zeus.getCompanyid()}).then(response => {
          let dataList=response.body.info;
          this.financeDataList=dataList.filter((element,index)=>{
//            if(index==0){
//              element.process='1,2'
//            }
//            if(index==1){
//              element.process='3'
//            }
            if(!element.process){
              element.process='1,2,3'
            }
            if(this.showType()==0||element.process.indexOf(String(this.showType()))>-1){
              return true;
            }else{
              return false;
            }
          });
          this.$indicator.close();
        }, response => {
          CommonUtil.handlerError(this,response);
          this.$indicator.close();
        });
      },
      showType(){
        //是否来自于 0:全部 1:新车 2:二手车 3:车主贷
        console.log("fromType="+this.$route.query.fromType);
        if(this.$route.query.fromType){
          return this.$route.query.fromType;
        }else{
          return 0;
        }
      }
    },
    created(){
    },
    mounted(){
      this.getData();
    },
    destroyed(){
    },
    components: {Titlebar, FinanceAuthItem, Divider},
  }
</script>

<style lang="less" rel="stylesheet/less" scoped>
  @import "../../assets/css/common.less";

</style>
