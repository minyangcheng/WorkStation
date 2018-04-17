var _ = require('lodash');
var util = require('util');

var users = [
  { 'user': 'barney',  'age': 36 ,err(){},'job':{name:'ni',succes(dd){},'p':{aa(){}}}},
  { 'user': 'fred',    'age': 40 },
  { 'user': 'pebbles', 'age': 18 }
];

console.log('--------------------1----------------');
var names = _.chain(users)
  .map(function(user){
    return user.user;
  })
  .join(" , ")
  .value();
console.log(names);

console.log('--------------------2----------------');
var youngest2 = _.chain(users)
  .sortBy("age")
  .map(function(user){
    return user;
  })
  .filter(function (user) {
    return user.user=='pebbles';
  })
  .value();
console.log(youngest2);

console.log('--------------------3----------------');
var userObj = _.chain(users)
  .map(function(user){
    return [user.user, user.age];
  })
  .zipObject()
  .value();
console.log(userObj);

console.log('--------------------4----------------');
function Foo() {
  this.a = 1;
  this.b = 2;
}

Foo.prototype.c = 3;

var arr=_.keysIn(new Foo);
console.dir(arr)

console.log('--------------------4----------------');
var jsonStr=util.inspect(users[0], false,0,true);
console.log(jsonStr)

console.log('--------------------5----------------');
function Foo() {
  this.a = 1;
  this.b = 2;
}
Foo.prototype.c = 3;
console.log(_.keysIn(new Foo()))
console.log(Object.keys(new Foo()))

_.mapKeys({ 'a': 1, 'b': 2 }, function(value, key) {
  console.log(key + value)
  return key + value;
});
