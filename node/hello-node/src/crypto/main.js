const crypto = require('crypto');

const hmac = crypto.createHmac('sha256', '1');

hmac.update('Hello, world!');
hmac.update('Hello, nodejs!');

console.log(hmac.digest('hex'));
