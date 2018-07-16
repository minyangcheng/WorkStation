const assert = require('assert');
const sum = require('../src/test_/hello');

describe('#hello.js', () => {
  describe('#sum', () => {
    before(function () {
      console.log('before:');
    });

    after(function () {
      console.log('after.');
    });

    beforeEach(function () {
      console.log('  beforeEach:');
    });

    afterEach(function () {
      console.log('  afterEach.');
    });

    it('sum() should return 0', async () => {
      let r = await sum(0,0);
      assert.strictEqual(r, 0);
    });

    it('#async function', async () => {
      let r = await sum(5,10);
      assert.strictEqual(r, 15);
    });
  });
});
