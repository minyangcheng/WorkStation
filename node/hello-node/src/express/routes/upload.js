var express = require('express');
var router = express.Router();
var multer = require('multer');
var fs = require('fs');

var createFolder = function (folder) {
  try {
    fs.accessSync(folder);
  } catch (e) {
    fs.mkdirSync(folder);
  }
};

var uploadFolder = './my-upload-dest';

createFolder(uploadFolder);

var storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, uploadFolder);
  },
  filename: function (req, file, cb) {
    cb(null, file.originalname)
  }
});

var upload = multer({ storage: storage })

router.post('/single', upload.single('file'), function (req, res, next) {
  var file=req.file;
  console.log('fileName：%s, originalName:%s ,filePath=%s',file.fieldname,file.originalname,file.path)
  res.send({message: 'upload success'});
});

module.exports = router;
