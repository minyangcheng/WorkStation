/**
 * Created by cheguo on 2017/5/5.
 */
export default {

  checkEmail(val) {
    var filter = /^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if (filter.test(val)) {
      return true;
    } else {
      return false;
    }
  },

  checkPhone(val) {
    var filter = /^1\d{10}$/;

    if (filter.test(val)) {
      return true;
    } else {
      return false;
    }
  },

  checkCardId(card){
  // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
  var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
  if(reg.test(card)){
    return  true;
  }else{
    return false;
  }
}

}
