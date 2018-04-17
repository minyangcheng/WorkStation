function sum(x){
  var y = function(x){
    return sum(x+y)
  }
  y.toString = y.valueOf = function(){
    return x;
  }
  return y;
}
console.log(sum(1)())
