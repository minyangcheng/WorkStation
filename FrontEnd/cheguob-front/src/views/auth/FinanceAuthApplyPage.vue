<template>
  <div class="page" style="background: #fff">
    <Divider/>
    <div style="padding: 15px;background: #fff">
      <div style="font-size: 14px;color: #333333">{{!financialinfo?'':financialinfo.financialname}}</div>
      <div style="font-size: 12px;color: #666666;margin-top: 15px">
        {{!financialinfo?'':financialinfo.description}}
      </div>
    </div>
    <div class="btn-orange" style="bottom: 0" @click="clickApply">申请认证</div>
  </div>
</template>

<script type="text/ecmascript-6">
  import Titlebar from "./../../components/Titlebar.vue";
  import Divider from "./../../components/Divider.vue";
  import Zeus from "./../../utils/Zeus";
  import CommonUtil from './../../utils/CommonUtil';

  export default {
    data(){
      return {
        financialinfo:null
      }
    },
    computed: {},
    methods: {
      clickApply(){
        let financialinfo = {
          verifystatus: this.financialinfo.verifystatus,
          userid: Zeus.getUserid(),
          companyid: Zeus.getCompanyid(),
          financialid: this.financialinfo.financialid,
          applyid: this.financialinfo.applyid,
        };
        this.$indicator.show();
        this.$http.post("/dealer/authfinancialcompany.json", {financialinfo: JSON.stringify(financialinfo)}).then(response => {
          this.$indicator.close();
          console.log(response);
          this.$toast(response.message);
          if(this.isFromVue()){
            this.$router.go(-1);
          }else{
            this.$router.replace({path: "/FinanceAuthInfoPage", query: {financialinfo: financialinfo}});
          }
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
          for(let i=0; i<dataList.length;i++){
            if(dataList[i].financialname.indexOf('晟安')>-1){
              this.financialinfo=dataList[i];
              console.log(dataList[i]);
              if(this.financialinfo.verifystatus!=0){
                this.$router.replace({path: "/FinanceAuthInfoPage", query: {financialinfo: this.financialinfo}});
              }
            }
          }
          this.$indicator.close();
        }, response => {
          CommonUtil.handlerError(this,response);
          this.$indicator.close();
        });
      },
      isFromVue(){
        return !!this.$route.query.financialinfo;
      }
    },
    created(){
      if(this.isFromVue()){
        this.financialinfo=this.$route.query.financialinfo;
      }else{
        this.getData();
      }
      console.log('created');
      console.log(this.$route.query.financialinfo);
    },
    mounted(){
      console.log('mounted');
      console.log(this.$route.query.financialinfo);
    },
    destroyed(){
    },
    components: {Titlebar, Divider},
  }
</script>

<style lang="less" rel="stylesheet/less" scoped>
  @import "../../assets/css/common.less";

</style>
