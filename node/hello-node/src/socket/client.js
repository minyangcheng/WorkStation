var io = require('socket.io-client');
var socket = io('ws://10.10.13.125:53079');

socket.on('chat',function(data){
    console.log(data);
});

// socket.emit('chat', {rp:"fine,thank you"});

