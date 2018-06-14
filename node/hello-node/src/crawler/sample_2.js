var async = require('async');
// console.time('series');
// async.series({
//   one: function (callback) {
//     callback(null, 'one');
//   },
//   two: function (callback) {
//     callback(null, 'two');
//
//   },
// }, function (error, result) {
//   console.log('error: ' + error);
//   console.log('result: ' + JSON.stringify(result));
//   console.timeEnd('series');
// });

// console.time('waterfall');
// async.waterfall([
//   function(callback) {
//     callback(null, 'one');
//   },
//   function(onearg, callback) {
//     callback(null, onearg + '>>>two');
//   },
//   function(twoarg, callback) {
//     callback(null, twoarg + '>>>three');
//   },
//   function(threearg, callback) {
//     callback(null, threearg + '>>>four');
//   }
// ], function(error, result) {
//   console.log('error: ' + error);
//   console.log('result: ' + result);
//   console.timeEnd('waterfall');
// });

console.time('parallel');
async.parallel({
  one: function(callback) {
    //处理逻辑
    callback(null, 'one');
  },
  two: function(callback) {
    //处理逻辑
    callback(null, 'tow');
  },
  three: function(callback) {
    //处理逻辑
    callback(null, 'three');
  },
  four: function(callback) {
    //处理逻辑
    callback(null, 'four');
  }
}, function(error, result) {
  console.log('one:', result.one);
  console.log('two:', result.two);
  console.log('three:', result.three);
  console.log('four:', result.four);
  console.log('error: ' + error);
  console.log('result: ' + JSON.stringify(result));
  console.timeEnd('parallel');
});
