var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var ioHandler= require('./ioserver');

app.get('/*', function (req, res) {
    res.sendFile(__dirname + '/index.html');
});

ioHandler(io);

http.listen(3000, function () {
    console.log('listening on *:3000');
});
