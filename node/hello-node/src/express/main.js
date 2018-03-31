var path = require('path');
var express = require('express');
var app = express();
var routes = require('./routes/index');

app.use('/static', express.static(path.join(__dirname, 'public')));
app.use('/*', function (req, res, next) {
  console.log('middleware....');
  next();
});
app.use('/',routes);

app.get('/a', async function (req, res) {
    var str=await timePromise();
    res.send(str);
});

function timePromise() {
  return new Promise(function (resolve, reject) {
    setTimeout(()=>resolve('nihao'),5000);
  })
}

app.listen(8000, function () {
    console.log('listening at port 8000');
});
