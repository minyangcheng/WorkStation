var fs = require("fs");
var buffArr = [];

var readerStream = fs.createReadStream('input.txt');
// readerStream.setEncoding('UTF8');

readerStream.on('data', function (chunk) {
  buffArr.push(chunk)
});

readerStream.on('end', function () {
  var buff = Buffer.concat(buffArr);
  console.log('readStream-->')
  console.log(buff);
});

readerStream.on('error', function (err) {
  console.log(err.stack);
});

var buff=fs.readFileSync('input.txt');
console.log('readFile--->');
console.log(buff);
