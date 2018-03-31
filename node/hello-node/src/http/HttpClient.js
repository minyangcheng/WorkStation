var http = require('http');

var options = {
  host: 'localhost',
  port: '8999',
  path: '/test',
  method: 'post',
  headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
};

var req = http.request(options, function (response) {
  console.log('status: ' + response.statusCode);
  console.log('headers: ' + JSON.stringify(response.headers));
  var body = '';
  response.on('data', function (data) {
    body += data;
  });
  response.on('end', function () {
    console.log(body);
  });
});

req.on('error', function (e) {
  console.log('problem with request: ' + e.message);
});

req.write('age=2&min=民');
req.end();
