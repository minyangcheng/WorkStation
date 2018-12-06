function compareVersion(v1, v2) {
  v1 = v1.split('.')
  v2 = v2.split('.')
  var len = Math.max(v1.length, v2.length)

  while (v1.length < len) {
    v1.push('0')
  }
  while (v2.length < len) {
    v2.push('0')
  }

  for (var i = 0; i < len; i++) {
    var num1 = parseInt(v1[i])
    var num2 = parseInt(v2[i])

    if (num1 > num2) {
      return 1
    } else if (num1 < num2) {
      return -1
    }
  }
  return 0
}

let v1='1.10.20';
let v2='1.9.9';
//主版本号.次版本号.修订号
console.log(compareVersion('1.10.20', '1.9.9'))
console.log(v1.localeCompare(v2))

let arr=[v1,v2,'2.0.0'];
let result=arr.sort((v1,v2)=>{
  return compareVersion(v2,v1);
})
console.log(result);


