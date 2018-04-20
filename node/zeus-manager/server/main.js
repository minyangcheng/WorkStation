var path = require('path');
var express = require('express');
var bodyParser = require('body-parser');

var app = express();

app.all('*', (req, res, next) => {
  res.header('Access-Control-Allow-Origin', '*');
  res.header('Access-Control-Allow-Headers', 'X-Requested-With');
  res.header('Access-Control-Allow-Methods', 'PUT,POST,GET,DELETE,OPTIONS');
  next();
});

app.use(bodyParser.urlencoded({ extended: false }))
app.use(bodyParser.json())

var indexRoute = require('./routes/index');
var uploadRoute = require('./routes/upload');
var checkUpdate = require('./routes/checkUpdate');
app.use('/static', express.static(path.join(__dirname, 'public')));
app.use('/', indexRoute);
app.use('/upload', uploadRoute);
app.use('/checkUpdate', checkUpdate);

app.use(function (req, res) {
  res.send('not found');
})

app.listen(8081, function () {
  console.log('listening at port 8081');
});
