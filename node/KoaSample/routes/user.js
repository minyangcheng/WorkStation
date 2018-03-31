var router = require('koa-router')();
var t_user_review_config = require('../models/user');
// var db = require('../config/db');

router.prefix('/users')

router.get('/user/:userId', async function (ctx, next) {
  var config = await t_user_review_config.findOne({
    where: {
      userId: ctx.params.userId,
      config_type: "0"
    }
  });
  console.log(config.id + config.userId);
  ctx.state = {
    userName: config.userName
  };

  ctx.response.body = JSON.stringify(config);
})
module.exports = router;
