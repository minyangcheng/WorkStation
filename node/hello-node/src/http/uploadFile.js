var http=require('http');
var fs=require('fs');

function upload() {
  var boundaryKey = '----' + new Date().getTime();
  var options = {
    host: 'localhost',//远端服务器域名
    port: 8081,//远端服务器端口号
    method: 'POST',
    path: '/upload/single',//上传服务路径
    headers: {
      'Content-Type': 'multipart/form-data; boundary=' + boundaryKey,
      'Connection': 'keep-alive'
    }
  };
  var req = http.request(options, function (res) {
    res.setEncoding('utf8');
    res.on('data', function (chunk) {
      console.log('body: ' + chunk);
    });
    res.on('end', function () {
    });
  });
  req.write(
    '--' + boundaryKey + '\r\n' +
    'Content-Disposition: form-data; name="file"; filename="gradle-3.1-all.zip"\r\n' +
    'Content-Type: application/x-zip-compressed \r\n\r\n'
  );
  var fileStream = fs.createReadStream('./files/gradle-3.1-all.zip');
  fileStream.pipe(req, {end: false});
  fileStream.on('end', function () {
    req.end('\r\n--' + boundaryKey + '--');
  });
}

upload();

