import $ from 'zeptojs'
import style from './style.css'

export default {

  getItemInfo(name,index){
    return 'name='+name+' , index='+index;
  },

  addItemToBody(name,index){
    var node=$("<div></div>").text(this.getItemInfo(name,index)).addClass('item').height(100);
    $(document.body).append(node)
  }

}
