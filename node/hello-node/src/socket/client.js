var io = require('socket.io-client');
var socket = io('ws://10.10.12.165:3000');

socket.on('chat',function(data){
    console.log(data);
});

socket.emit('chat', {rp:"fine,thank you"});

