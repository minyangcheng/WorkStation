const http = require('http');
const fs = require('fs');

//post地址为本地服务的一个php，用于测试上传是否成功
var options = {
  hostname: 'localhost',
  port: 80,
  path: '/get.php',
  method: 'POST'
}

//生成分隔数据
var boundaryKey = '----WebKitFormBoundaryjLVkbqXtIi0YGpaB';

//读取需要上传的文件内容
fs.readFile('./upload.txt', function (err, data) {
  //拼装分隔数据段
  var payload = '--' + boundaryKey + '\r\n' + 'Content-Disposition:form-data; name="myfile"; filename="upload.txt"\r\n' + 'Content-Type:text/plain\r\n\r\n';
  payload += data;
  payload += '\r\n--' + boundaryKey + '--';

  //发送请求
  var req = http.request(options, function (res) {
    res.setEncoding('utf8');
    res.on('data', function (chunk) {
      console.log('body:' + chunk);
    });

  });

  req.on('error', function(e) {
    console.error("error:"+e);
  });
  //把boundary、要发送的数据大小以及数据本身写进请求
  req.setHeader('Content-Type', 'multipart/form-data; boundary='+boundaryKey+'');
  req.setHeader('Content-Length', Buffer.byteLength(payload, 'utf8'));
  req.write(payload);
  req.end();
});
