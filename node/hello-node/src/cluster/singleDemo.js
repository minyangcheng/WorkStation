var http = require('http')

http.createServer(function(req, res) {
  sleep(500);
  res.writeHead(200);
  res.end('worker:'+process.pid);
}).listen(9001);

function sleep(mill) {
  var endDate=Date.now()+mill;
  while (Date.now()<endDate);
}
