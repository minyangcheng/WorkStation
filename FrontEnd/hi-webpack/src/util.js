import $ from 'zeptojs'
import logoImage from './assets/logo.png'

console.log('util->' + logoImage);

export default {

  getItemInfo(name, index) {
    return 'name=' + name + ' , index=' + index;
  },

  addItemToBody(name, index) {
    var node = $("<div></div>").text(this.getItemInfo(name, index)).addClass('item').height(100);
    $(document.body).append(node);
    node = '<img src="assets/logo.png">';
    $(document.body).append(node);
  }

}
