let EventBus = require('./event.js');

module.exports=function () {
    EventBus.emit('eventName',{name:1231,age:11});
}
