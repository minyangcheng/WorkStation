var express = require('express');
var router = express.Router();
var fs=require('fs');
var path=require('path');

router.get('/', function (req, res) {
  res.sendFile(path.join(__dirname,'../public/index.html'));
});

module.exports = router;
