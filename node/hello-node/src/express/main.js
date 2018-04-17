var path = require('path');
var express = require('express');

var app = express();

var indexRoute = require('./routes/index');
var uploadRoute = require('./routes/upload');
app.use('/static', express.static(path.join(__dirname, 'public')));
app.use('/', indexRoute);
app.use('/upload', uploadRoute);

app.use(function (req,res) {
  res.send('not found');
})

app.listen(8081, function () {
  console.log('listening at port 8081');
});
