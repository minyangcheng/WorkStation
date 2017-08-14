<template>
  <div class="page" style="overflow: auto;background: #fff">
    <div v-if="status==1||status==3" :class="{authing:(status==1),authFail:(status==3)}">{{status==1?'个人认证中，请耐心等待':'个人资料真实性有误，请重新提交认证'}}</div>
    <Divider/>
    <HeaderInfo title="基本信息"></HeaderInfo>
    <Divider/>
    <EditLayout ref="nameInput" leftText="姓名" placeholder="请输入姓名" :enable="canEdit"></EditLayout>
    <Divider/>
    <div style="position: relative">
      <EditLayout ref="idCardInput" leftText="身份证号" placeholder="请输入身份证号" style="padding-right: 40px"
                  :enable="canEdit"></EditLayout>
      <img v-show="canEdit" @click="clickOcr" class="ocr" src="./../../assets/img/ic_ocr_camera.png"/>
    </div>
    <Divider/>
    <EditLayout v-show="false" ref="phoneInput" leftText="手机号码" placeholder="请输入手机号码" :enable="canEdit"></EditLayout>
    <Divider v-show="false"/>
    <HeaderInfo title="身份证照片"></HeaderInfo>
    <Divider/>
    <UploadImageArea ref="uploadImageAreaRef" :dataList="imageDataList" :enable="canEdit"></UploadImageArea>
  </div>
</template>

<script type="text/ecmascript-6">
  import Titlebar from "./../../components/Titlebar.vue";
  import HeaderInfo from '../../components/HeaderInfo.vue';
  import Divider from '../../components/Divider.vue';
  import Zeus from '../../utils/Zeus';
  import CommonUtil from './../../utils/CommonUtil';
  import CheckUtil from './../../utils/CheckUtil';
  import EditLayout from '../../components/EditLayout.vue';
  import UploadImageArea from '../../components/UploadImageArea.vue';
  import idcordFrontSample from "../../assets/img/img_idcard_front.png";
  import idcordReverseSample from "../../assets/img/img_idcard_reverse.png";

  export default {
    data(){
      return {
        status: 0,
        imageDataList: [{title: "身份证正面", holderImg: idcordFrontSample, uploadUrl: ''},
                        {title: "身份证反面", holderImg: idcordReverseSample, uploadUrl: ''}],
        id: 0,
        rightBtnFun:''
      }
    },
    computed: {
      canEdit(){
        if (this.status == 0 || this.status == 3) {
          console.log('can edit  '+this.status);
          return true;
        } else {
          console.log('can not edit  '+ this.status);
          return false;
        }
      },
    },
    methods: {
      uploadForm(){
        if (!this.check()) return;
        let uploadData = {
          companyid: Zeus.getCompanyid(),
          userid: Zeus.getUserid(),
          id: this.id,
          username: this.$refs.nameInput.getValue(),
          idcard: this.$refs.idCardInput.getValue(),
          phone: this.$refs.phoneInput.getValue(),
          frontpic: this.imageDataList[0].uploadUrl,
          oppositepic: this.imageDataList[1].uploadUrl,
        };
        //个人认证
        this.$indicator.show();
        this.$http.post("/dealer/authcompanypersoninfo.json", {personinfo: JSON.stringify(uploadData)}).then(response => {
          this.$indicator.close();
          this.$toast(response.message);
          this.$router.go(-1);
        }, response => {
          CommonUtil.handlerError(this,response);
          this.$indicator.close();
        });
      },
      check(){
        let username = this.$refs.nameInput.getValue();
        let idcard = this.$refs.idCardInput.getValue();
        let phone = this.$refs.phoneInput.getValue();
        if (!username) {
          this.$toast('姓名不能为空');
          return false;
        }
        if (!idcard) {
          this.$toast('身份证号码不能为空');
          return false;
        }
        if (!CheckUtil.checkCardId(idcard)) {
          this.$toast('身份证号码格式不对');
          return false;
        }
        if (!phone) {
          this.$toast('手机号码不能为空');
          return false;
        }
        if(!CheckUtil.checkPhone(phone)){
          this.$toast('手机号码格式不对');
          return false;
        }
        let mess=this.$refs.uploadImageAreaRef.check();
        if(mess){
          this.$toast(mess);
          return false;
        }
        return true;
      },
      clickOcr(){
        let self = this;
        window.PersonalAuthPage_OCR = function (name, idcard) {
          self.$refs.nameInput.setValue(name);
          self.$refs.idCardInput.setValue(idcard);
        };
        Zeus.ocrRecognition("PersonalAuthPage_OCR");
      },
      setDataToViews(data){
        this.$refs.nameInput.setValue(data.username);
        this.$refs.idCardInput.setValue(data.idcard);
        this.$refs.phoneInput.setValue(data.phone);
        this.imageDataList[0].uploadUrl = data.frontpic;
        this.imageDataList[1].uploadUrl = data.oppositepic;
        this.id = data.id;
        this.status=data.verifystatus;
        if (this.status == 0 || this.status == 3) {
          Zeus.showRightButton(this.$route.meta.rightBtnName,this.$route.meta.rightBtnFun);
        } else {
          Zeus.showRightButton('','');
        }
      },
      getData(){
        //获取车商个人认证信息
        this.$indicator.show();
        this.$http.post("/dealer/getcompanypersoninfo.json", {companyId: Zeus.getCompanyid(),userid:Zeus.getUserid()}).then(response => {
          if(response.body){
            this.setDataToViews(response.body);
          }
          this.$indicator.close();
        }, response => {
          CommonUtil.handlerError(this,response);
          this.$indicator.close();
        });
      }
    },
    created(){
      this.rightBtnFun=this.$route.meta.rightBtnFun;
    },
    mounted(){
      let self=this;
      window[this.rightBtnFun]=function () {
        self.uploadForm();
      };
      this.getData();
    },
    destroyed(){
      delete window[this.rightBtnFun];
      delete window.PersonalAuthPage_OCR;
    },
    components: {Titlebar, HeaderInfo, Divider, EditLayout, UploadImageArea},
  }
</script>

<style lang="less" type="text/less" scoped>
  @import "../../assets/css/common.less";
  .authing{
    padding: 6px 17px;
    color: #222222;
    font-size: 14px;
    background: #fff3dd;
  }
  .authFail{
    padding: 6px 17px;
    color: #222222;
    font-size: 14px;
    background: #fff3dd;
  }
  .ocr {
    .vertical-center;
    width: 20px;
    height: 20px;
    right: 15px;
  }
</style>
