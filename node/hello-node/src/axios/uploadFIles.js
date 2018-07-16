var fs = require('fs');
var path = require('path');
var httpClient = require('./httpClient');

var extension = '.apk';
var filePath = path.resolve('./chengshang_2.6.9_201806141131_offical_sign.apk');

httpClient.uploadFile_('http://10.10.13.8:8083/upload/uploadfileforaliyun.do?filesource=10f4fe1edeae11e5b7be086266812821&extname=' + extension, filePath)
  .then(resp => {
    var data = resp.data;
    console.log(JSON.stringify(data));
  })
  .catch(err => {
    console.log(err)
  });
