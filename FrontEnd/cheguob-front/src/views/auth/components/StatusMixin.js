/**
 * Created by cheguo on 2017/5/3.
 */
export default {
  computed: {
    statusStr(){
      let str;
      switch (this.status){  //0 未认证，1 认证中，2 已认证，3 认证失败
        case 0:
          str="认证";
          break;
        case 1:
          str="认证中";
          break;
        case 2:
          str="已认证";
          break;
        case 3:
          str="认证失败";
          break;
        default:
          str='';
          break;
      }
      return str;
    },
    clazzObject(){
      return {
        'auth-no':this.status==0,
        'auth-ing':this.status==1,
        'status-success':this.status==2,
        'auth-fail':this.status==3,
      }
    }
  },
}
