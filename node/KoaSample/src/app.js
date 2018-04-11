const path = require('path');
const Koa = require('koa');
const app = new Koa();
const autoScanFile = require('./middlewrae/autoScanFile');
const bodyParser = require('koa-bodyparser');
const rest = require('./middlewrae/rest');
const autoScanController = require('./middlewrae/autoScanController');

app.context.logger = require('./util/logUtil');

app.on('error', function (err, ctx) {
  console.log(err);
});

app.use(async (ctx, next) => {
  try {
    await next();
  } catch (err) {
    ctx.app.emit('error', err, ctx);
  }
});

app.use(async (ctx, next) => {
  console.log(`----------->${ctx.method} ${ctx.url}`)
  const start = new Date()
  await next()
  const ms = new Date() - start
  console.log(`<------------${ctx.method} ${ctx.url} - ${ms}ms`)
});

app.use(autoScanFile('/static', __dirname + '/static'));
app.use(bodyParser());
app.use(rest.restify());
app.use(autoScanController(__dirname + '/controller'));

app.use((ctx, next) => {
  ctx.throw(404);
});

module.exports = app;
