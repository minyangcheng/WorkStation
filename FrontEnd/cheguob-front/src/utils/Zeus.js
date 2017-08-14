export default {

  /**
   * 拍照
   * @param funName
   */
  takePicture(funName){
    if(window.cgwapp){
      window.cgwapp.takePicture(funName);
    }else{
      let data={
        "code":0,
        "fileserver":"http://cdn-file.cheguo.com/",
        "handle":"1",
        "message":"success",
        "path":"files/2017-05-10/201705101718541803560.jpg",
        "realPath":"http://cheguo-image.cheguo.com/files/2017-05-10/201705101718541803560.jpg",
        "success":true,
        "thumbnailserver":"http://cheguo-image.cheguo.com/"
      };
      window[funName](JSON.stringify(data));
      console.log("请在手机上调试此功能");
    }
  },

  /**
   * 展现右侧
   * @param btnName
   * @param funName
   */
  showRightButton(btnName,funName){
    if(window.cgwapp){
      window.cgwapp.showRightButton(btnName,funName);
    }else{
      console.log("请在手机上调试此功能");
    }
  },

  /**
   * 获取客户端类型 1-android 2-ios
   * @returns {*}
   */
  getClientType(){
    if(window.cgwapp){
      window.cgwapp.getClientType();
    }else{
      console.log("请在手机上调试此功能");
    }
  },

  /**
   * 设置原生标题栏标题
   * @param title
   */
  setToolbarTitle(title){
    if(window.cgwapp){
      window.cgwapp.setToolbarTitle(title);
    }else{
      console.log("请在手机上调试此功能");
    }
  },

  /**
   * 设置原生是否支持刷新
   * @param flag
   */
  setCanRefresh(flag){
    if(window.cgwapp){
      window.cgwapp.setCanRefresh(flag);
    }else{
      console.log("请在手机上调试此功能");
    }
  },

  /**
   * ocr识别
   * @param fun
   */
  ocrRecognition(fun){
    if(window.cgwapp){
      window.cgwapp.ocrRecognition(fun);
    }else{
      console.log("请在手机上调试此功能");
    }
  },

  /**
   * 隐藏原生标题栏
   */
  hideToolbar(){
    if(window.cgwapp){
      window.cgwapp.hideToolbar();
    }else{
      console.log("请在手机上调试此功能");
    }
  },

  /**
   * 返回
   */
  goBack(){
    if(window.cgwapp){
      window.cgwapp.goBack();
    }else{
      console.log("请在手机上调试此功能");
    }
  },

  /**
   * 展示图片
   * @param arr
   * @param index
   */
  showPictures(arr,index){
    if(!arr) return;
    if(window.cgwapp){
      window.cgwapp.showPictures(JSON.stringify(arr),index);
    }else{
      console.log("请在手机上调试此功能");
    }
  },

  showPictures(arr,index){
    if(!arr) return;
    if(window.cgwapp){
      window.cgwapp.showPictures(JSON.stringify(arr),index);
    }else{
      console.log("请在手机上调试此功能");
    }
  },

  getUserid(){
    if(window.cgwapp){
      return window.cgwapp.getUserid();
    }else{
      console.log("请在手机上调试此功能");
      // return 12560;
      return 12757;
    }
  },

  getCompanyid(){
    if(window.cgwapp){
      return window.cgwapp.getCompanyid();
    }else{
      console.log("请在手机上调试此功能");
      // return 2649;
      return 4686;
    }
  },

  /**
   * 跳转到企业认证界面
   */
  goToCompanyAuthPage(){
    if(window.cgwapp){
      if(cgwapp.getClientType()==1){
        //android
        let data={canonicalName:"com.cgw360.cheguob.ui.auth.CompanyAuthActivity"};
        window.cgwapp.openView(JSON.stringify(data));
      }else{
        //ios
        window.cgwapp.makeVerify();
      }
    }else{
      console.log("请在手机上调试此功能");
    }
  },

  /**
   * 弹出原生对话框
   * @param title
   * @param content
   */
  showAlertMsg(title,content){
    if(window.cgwapp){
      window.cgwapp.showAlertMsg(title,content);
    }else{
      alert(content);
    }
  },

  /**
   * 弹出原生对话框
   * @param title
   * @param content
   */
  alertMsg(content){
    if(window.cgwapp){
      window.cgwapp.showAlertMsg('提示',content);
    }else{
      alert(content);
    }
  },

  /**
   * 弹出原生对话框
   * @param title
   * @param content
   */
  isAdmin(){
    if(window.cgwapp){
      let value=window.cgwapp.isAdmin();
      if(typeof value =='boolean'){
        return value;
      }else{
        return value.toString()=='true';
      }
    }else{
      return true;
    }
  }

}
