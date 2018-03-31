var path = require('path');
var express = require('express');
var app = express();

app.get('/', function (req, res,next) {
    // try {
        throw new Error('dddd');
        res.send('special');
    // } catch (e) {
        // next(e);
    // }
});

app.use(function (err, req, res, next) {
    // console.error(err.stack);
    res.status(500).send('Something broke!');
});

app.listen(8000, function () {
    console.log('listening at port 8000');
});