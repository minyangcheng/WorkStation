var fs = require('fs');
var path = require('path');
var crypto = require('crypto');

function mdFile(path) {
  var data = fs.readFileSync(path);
  var hash = crypto.createHash('md5');
  var md5 = hash.update(data).digest('hex');
  return md5;
}

console.log(mdFile('./cheguoC_3.0.9_309_201804161746_market.apk'));
