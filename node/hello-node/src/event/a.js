let EventBus = require('./event.js');

module.exports=function () {
    EventBus.on('eventName', function (data) {
        console.log('receive mess:' + JSON.stringify(data));
    });
}