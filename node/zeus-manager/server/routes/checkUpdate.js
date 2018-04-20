var express = require('express');
var router = express.Router();

router.post('/', function (req, res) {
  res.send({code:10000,message:'updateApi is success'});
});

module.exports = router;
