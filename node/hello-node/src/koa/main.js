const Koa = require('koa');
const app = new Koa();
const json = require('./json');
const bodyparser = require('koa-bodyparser');

app.use(async (ctx,next) => {
  console.log('1111111111111')
  next();
});

app.use(json());
app.use(bodyparser({
  enableTypes: ['json', 'form', 'text']
}));

app.use(async (ctx,next) => {
  console.log('22222222222222');
  next();
});

app.use(async ctx => {
  console.log('33333333');
  ctx.body = { foo: 'bar' };
});

app.listen(3000);
console.log('listen * :3000');
