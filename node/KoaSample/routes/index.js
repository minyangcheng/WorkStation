var router = require('koa-router')();
// var db = require('../config/db');

router.get('/', async function (ctx) {
  ctx.state = {
    title: 'koa2 title'
  };
  //根据model自动在数据库中创建表  force：是否强制建表，即删除原来表
  //db.sync({force: true});
  await ctx.render('index', {title: 'Hello Koa 2!'})
})
module.exports = router;
