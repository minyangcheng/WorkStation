var router = require('koa-router')();
// var db = require('../config/db');

router.prefix('/api');

router.get('/', function (ctx, next) {
  ctx.body = 'this a api response!';
});

module.exports = router;
