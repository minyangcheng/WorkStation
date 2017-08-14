/**
金融认证 列表
*/
<template>
  <div class="page">
    <Divider big/>
    <FinanceAuthItem :title="financeDataList[0]&&financeDataList[0].financialname"
                     :content="financeDataList[0]&&financeDataList[0].description"
                     :status="financeDataList[0]?financeDataList[0].verifystatus:-1"
                     @clickItem="clickItem(financeDataList[0])"></FinanceAuthItem>
    <Divider/>
    <FinanceAuthItem :title="financeDataList[1]&&financeDataList[1].financialname"
                     :content="financeDataList[1]&&financeDataList[1].description"
                     :status="financeDataList[1]?financeDataList[1].verifystatus:-1"
                     @clickItem="clickItem(financeDataList[1])"></FinanceAuthItem>
    <Divider/>
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
    computed: {},
    methods: {
      clickItem(item){
        if (!item) return;
        if (item.verifystatus === 0 || item.verifystatus == 3) {
          //跳转到金融认证页面
          this.$router.push({path: "/FinanceAuthApplyPage", query: {financialinfo: item}});
        } else {
          //跳转到金融认证信息页面
          this.$router.push({path: "/FinanceAuthInfoPage", query: {financialinfo: item}});
        }
      },
      getData(){
        //获取认证金融机构
        this.$indicator.show();
        this.$http.post("/dealer/getfinancialinstitution.json", {companyid:Zeus.getCompanyid()}).then(response => {
          this.financeDataList=response.body.info;
          this.$indicator.close();
        }, response => {
          CommonUtil.handlerError(this,response);
          this.$indicator.close();

        });
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
