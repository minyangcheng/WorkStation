const request = require('supertest');
const server = require('../src/test_/app');

describe('#test server', () => {
  // var server=app.listen(3000);
  it('#test GET /', async () => {
    let res = await request(server)
      .get('/')
      .expect(200, '<h1>Hello, world!</h1>');
  });

  it('#test GET /name=Bob', async () => {
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
