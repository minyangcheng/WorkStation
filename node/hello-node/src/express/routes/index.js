var express = require('express');
var router = express.Router();

router.get('/', function(req, res, next) {
    res.send('route index');
});

module.exports = router;