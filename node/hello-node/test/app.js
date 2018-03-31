const Koa = require('koa');

const app = new Koa();

app.use(async (ctx, next) => {
  const start = new Date().getTime();
  await next();
  const ms = new Date().getTime() - start;
  console.log(`${ctx.request.method} ${ctx.request.url}: ${ms}ms`);
  ctx.response.set('X-Response-Time', `${ms}ms`);
});

app.use(async (ctx, next) => {
  console.log(ctx.request.path)
  if (ctx.request.path == '/getUserInfo') {
    console.log('json->'+ctx.request.path)
    ctx.response.type = 'json';
    ctx.response.body = {name: 'min', age: 1};
  } else {
    var name = ctx.request.query.name || 'world';
    ctx.response.type = 'text/html';
    ctx.response.body = `<h1>Hello, ${name}!</h1>`;
  }
});

// app.listen(3000);
console.log('app started at port 3000...');

module.exports = app;
