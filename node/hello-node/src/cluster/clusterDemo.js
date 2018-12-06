var cluster = require('cluster')
var http = require('http')
var numCPUs = require('os').cpus().length  //cpu鐨勬牳蹇冩暟

if (cluster.isMaster) {
  //鍒涘缓澶氫釜瀛愯繘绋�
  for (var i = 0; i < numCPUs; i++) {
    cluster.fork();
  }
  cluster.on('exit', function(worker, code, signal) {
    console.log('worker' + worker.process.id + 'died')
  })
} else {
  http.createServer(function(req, res) {
    sleep(500);
    res.writeHead(200);
    res.end('worker:'+process.pid);
  }).listen(9002);
}

function sleep(mill) {
  var endDate=Date.now()+mill;
  while (Date.now()<endDate);
}
