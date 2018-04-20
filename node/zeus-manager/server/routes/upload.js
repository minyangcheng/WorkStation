var express = require('express');
var router = express.Router();
var multer = require('multer');
var fs = require('fs');
var PkgReader = require('isomorphic-apk-reader');
var httpClient = require('../axios/httpClient');

var createFolder = function (folder) {
  try {
    fs.accessSync(folder);
  } catch (e) {
    fs.mkdirSync(folder);
  }
};

var uploadFolder = './upload-dest';

createFolder(uploadFolder);

var storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, uploadFolder);
  },
  filename: function (req, file, cb) {
    if (file)
      cb(null, file.originalname)
  }
});

var upload = multer({storage: storage});

router.post('/single', upload.single('file'), function (req, res, next) {
  var file = req.file;
  var originalname = file.originalname;
  if (!originalname) {
    return;
  }
  var extension = file.originalname.substring(originalname.lastIndexOf('.'));
  if (extension != '.apk' && extension != '.ipa') {
    return res.send({code: 20000, message: '请选择apk或ipa包'});
  }
  var type = extension == '.apk' ? 'android' : 'ios';
  var reader = new PkgReader(file.path, extension, {searchResource: true});
  reader.parse(function (err, pkgInfo) {
    if (err) {
      return res.send({code: 20000, message: '读取包信息出错', error: err.stack});
    }
    httpClient.uploadFile_('http://10.10.13.8:8083/upload/uploadfileforaliyun.do?filesource=10f4fe1edeae11e5b7be086266812821&extname=' + extension, file.path)
      .then(resp => {
        var data = resp.data;
        if (data && data.handle == '1') {
          var result = {
            code: 10000,
            name: (pkgInfo.application.label)[0],
            type: type,
            version: pkgInfo.versionName,
            appId: pkgInfo.package,
            destUrl: data.fileserver + data.path
          };
          res.send(result);
        } else {
          res.send({code: 20000, message: '上传阿里云失败'});
        }
      }).catch(err => {
      res.send({code: 20000, message: err.toString()});
    });

  });
});

module.exports = router;
