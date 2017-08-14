/**
信息认证
*/
<template>
  <div class="page">
    <Divider big/>
    <InfoAuthItem title="个人认证"
                  content="获得个人认证标识"
                  :image="authPersonal" @clickAuth="clickPersonalAuth" :status="personalAuthStatus"/>
    <Divider/>
    <InfoAuthItem v-show="isAdmin" title="企业认证"
                  content="获得企业认证标识、更多功能敬请期待"
                  :image="authCompany" @clickAuth="clickCompanyAuth" :status="companyAuthStatus"/>
    <Divider v-show="isAdmin"/>
    <InfoAuthItem v-show="isAdmin" title="高级认证"
                  content="提供新车分期、二手车分期、车主再分期等业务服务"
                  :image="authFinance" @clickAuth="clickFinanceAuth" :status="financeAuthStatus"/>
    <Divider v-show="isAdmin"/>
  </div>
</template>

<script>
  import Titlebar from "./../../components/Titlebar.vue";
  import Divider from "./../../components/Divider.vue";
  import InfoAuthItem from "././components/InfoAuthItem.vue";
  import authPersonal from "./../../assets/img/auth_personal.png";
  import authCompany from "./../../assets/img/auth_company.png";
  import authFinance from "./../../assets/img/auth_finance.png";
  import Zeus from "./../../utils/Zeus";
  import CommonUtil from "./../../utils/CommonUtil";

  export default {
    data(){
      return {
        authPersonal: authPersonal,
        authCompany: authCompany,
        authFinance: authFinance,
        personalAuthStatus:-1,
        companyAuthStatus:-1,
        financeAuthStatus:-1,
        isAdmin:false,
      }
    },
    computed: {
      aa() {
        return "";
      }
    },
    methods: {
      clickPersonalAuth(){
        this.$router.push({path: "/PersonalAuthPage"});
      },
      clickCompanyAuth(){
        Zeus.goToCompanyAuthPage();
      },
      clickFinanceAuth(){
        if(this.companyAuthStatus==2){
          this.$router.push({path: "/FinanceAuthListPage"});
        }else{
          Zeus.alertMsg('请先进行企业认证');
        }
      },
      getData(){
        this.$indicator.show();
        //获取车商认证状态
        this.$http.post("/dealer/getauthstatus.json", {companyId: Zeus.getCompanyid(),userid:Zeus.getUserid()}).then(response => {
          this.personalAuthStatus=response.body.personStatus;
          this.companyAuthStatus=response.body.companyStatus;
          let financeList=response.body.list;
          let tempStatus=0;
          if(financeList&&financeList.length>0){
            tempStatus=2;
            for(let i=0;i<financeList.length;i++){
              if(financeList[i].verifystatus!=2){
                tempStatus=0;
                console.log(financeList[i].verifystatus);
                break;
              }
            }
          }
          this.financeAuthStatus=tempStatus;
          console.log(this.financeAuthStatus);

          this.$indicator.close();
        }, response => {
          this.$indicator.close();
          CommonUtil.handlerError(this,response);
        });
      }
    },
    created(){
      Zeus.setToolbarTitle(this.$route.meta.title);
    },
    mounted(){
      this.isAdmin=Zeus.isAdmin();
      let self=this;
      window.manualRefresh=function () {
        self.getData();
      };
      this.getData();
    },
    destroyed(){
      delete window.manualRefresh;
    },
    components: {Titlebar, Divider, InfoAuthItem},
  }
</script>

<style lang="less" type="stylesheet/less" scoped>
  @import "../../assets/css/common.less";

</style>
