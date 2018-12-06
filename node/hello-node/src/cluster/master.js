var child_process = require('child_process');
var process = require('process');

for(var i=0; i<3; i++) {
  var worker_process = child_process.fork("b.js", [i]);
  worker_process.on('message', function (value) {
    console.log(value);
  });
  worker_process.on('close', function (code) {
    console.log('子进程已退出，退出码 ' + code);
  });
}
