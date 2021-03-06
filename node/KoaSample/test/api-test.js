const request = require('supertest');
const app = require('../src/app');

describe('#test koa app', () => {

  let server = app.listen(9900);

  describe('#test server', () => {

    it('#test GET /', async () => {
      let res = await request(server)
        .get('/')
        .expect('Content-Type', /text\/html/)
        .expect(200, '<h1>Hello, world!</h1>');
    });

    it('#test GET /path?name=Bob', async () => {
      let res = await request(server)
        .get('/path?name=Bob')
        .expect('Content-Type', /text\/html/)
        .expect(200, '<h1>Hello, Bob!</h1>');
    });

    it('#test GET /getUserInfo', async () => {
      let res = await request(server)
        .get('/getUserInfo')
        .expect('Content-Type', /json/)
        .expect(200, ({name: 'min', age: 1}));
    });

  });
});
