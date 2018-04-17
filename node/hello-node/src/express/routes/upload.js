var express = require('express');
var router = express.Router();
var multer = require('multer');

var upload = multer({dest: 'upload/'});

// router.post('/upload', upload.single('logo'), function(req, res, next){
//   console.log('1111111')
//   res.send({code: '10000'});
// });

module.exports = router;
