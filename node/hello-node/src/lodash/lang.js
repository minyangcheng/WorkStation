var _ =require("lodash")

var value=_.isObject(function () {

})

// console.log(Object.prototype.toString.call(new String('1')))

// console.log(typeof new String('a'))

var objects = [{ 'a': 1 }, { 'b': 2 }];

var deep = _.cloneDeep(objects);
console.log(deep[0] === objects[0]);

var depObj=Object.assign([],objects);
console.log(depObj[0]===objects[0])

console.log(_.toInteger('aba'))
