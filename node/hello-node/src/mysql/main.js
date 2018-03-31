var path = require('path');
var express = require('express');
var app = express();
var opt = require('./opt');

app.get('/*', function (req, res) {
    opt.query(function (error, results, fields) {
        if (error) throw error;
        res.send(results);
    })
});

app.listen(8000, function () {
    console.log('listening at port 8000');
});