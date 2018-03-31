var debug = require('debug');
var listDebug = debug('app:list');
var profileDebug = debug('app:profile');
var loginDebug = debug('account:login');

// 分别运行下面几行命令看下效果
//
//     DEBUG=* node 03.js
//     DEBUG=*,-account* node 03.js
//
listDebug('hello');
profileDebug('hello');
loginDebug('hello');

var debug = require('debug')('app');

// debug('my name is %s', 'chyingp');

debug(new Error('aa'));

console.log(new Error('aa'));


console.log(JSON.stringify((new Error('aa'))))
