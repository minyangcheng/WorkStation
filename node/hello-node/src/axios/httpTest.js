const httpClient = require('./httpClient');
const util = require('util');

httpClient.post('http://127.0.0.1:7001/user/register',{userName:'闵',pwd:'22',rePwd:'22'})
  .then(data=>{
    log(data);
  }).catch(err=>{
    log(err);
})

function log(data) {
  console.log(util.inspect(data,false,2,true));
}
