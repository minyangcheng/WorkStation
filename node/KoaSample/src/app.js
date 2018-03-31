const path = require('path');
const Koa = require('koa');
const app = new Koa();
const views = require('koa-views');
const bodyparser = require('koa-bodyparser');
const autoScanController = require('./autoScanController');
const {RespCode} = require('./global/constants');
const ApiError = require('./global/apiError');

app.context.logger = require('./util/logUtil');

const index = require('../routes/index');
const users = require('../routes/user');

app.use(async (ctx, next) => {
  try {
    await next();
  } catch (err) {
    ctx.response.status = err.statusCode || err.status || 500;
    if (err instanceof ApiError) {
      ctx.response.type = 'json';
      ctx.response.body = {code: err.code, message: err.message};
    } else {
      await ctx.render('error', {message: err.message, error: err});
    }
    ctx.app.emit('error', err, ctx);
  }
});

app.on('error', function (err, ctx) {
  ctx.logger.error(err);
});

app.use(bodyparser({
  enableTypes: ['json', 'form', 'text']
}));
app.use(require('koa-static')(path.join(__dirname, '../public')));
app.use(views(path.join(__dirname, '../views'), {
  extension: 'pug'
}));

app.use(async (ctx, next) => {
  const start = new Date()
  await next()
  const ms = new Date() - start
  console.log(`${ctx.method} ${ctx.url} - ${ms}ms`)
});

app.use(index.routes(), index.allowedMethods());
app.use(users.routes(), users.allowedMethods());
var scanRouter = autoScanController();
app.use(scanRouter.routes(), scanRouter.allowedMethods());

app.use((ctx, next) => {
  ctx.throw(404);
});

module.exports = app;
