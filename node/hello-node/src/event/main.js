var listenEvent=require('./a.js');
var sendEvent=require('./b.js');

listenEvent();
setTimeout(()=>{
    console.log('send event');
    sendEvent();
},5000);