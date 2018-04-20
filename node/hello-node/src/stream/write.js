var fs = require("fs");
var data = '菜鸟教程官网地址：www.runoob.com';
var buff=Buffer.from('我是中国人');

var writerStream = fs.createWriteStream('output.txt',{flags:'a'});
writerStream.write(data);
writerStream.write('\n');
writerStream.write(buff);
writerStream.write('\n');
writerStream.end();

writerStream.on('finish', function() {
  console.log("写入完成。");
  fs.writeFileSync('output.txt','i am chinese',{flag:'a'});
});

writerStream.on('error', function(err){
  console.log(err.stack);
});

console.log("程序执行完毕");
