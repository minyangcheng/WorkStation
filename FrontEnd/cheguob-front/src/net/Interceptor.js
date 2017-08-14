/**
 * Created by cheguo on 2017/4/18.
 */
import md5 from 'js-md5';
import {API_SERVER_URL,SOURCE,SECRET} from "./../Constant";

var source=SOURCE;
var secret=SECRET;

function getFormResult(data){
  data=data || {};
  putSource(data);
  putSignature(data);
  return getFormStrByObj(data);
}
function getFormStrByObj(data){
  var key;
  var keyArr=getSortKeyArr(data);
  var formStr="";
  for(var i=0;i<keyArr.length;i++){
    key=keyArr[i];
    formStr=formStr+key+"="+data[key]+"&";
  }
  formStr=formStr.substring(0,formStr.length-1);
  return formStr;
}
function putSource(data){
  data["source"]=source;
}
function putSignature(data){
  var formStr=getFormStrByObj(data);
  var signature=md5(formStr+md5(secret));
  data["signature"]=signature;
}
function getSortKeyArr(data){
  var keyArr=new Array();
  var key;
  for(key in data){
    keyArr.push(key);
  }
  keyArr=keyArr.sort(function(a,b){return a.localeCompare(b)});
  return keyArr;
}

export default function (Vue) {
  Vue.http.interceptors.push(function(request, next) {
    if(request.method==='POST'){
      request.body=getFormResult(request.body);
      request.headers.set('Content-Type', 'application/x-www-form-urlencoded');
      // continue to next interceptor
      next(function(response) {
        if(response.ok){
          let body=response.body;
          if(body.code==10000){
            response.body=body.data;
            response.message=body.message;
          }else{
            response.ok=false;
            response.body={code:body.code,message:body.message};
          }
        }else{
          response.body={code:-1,message:"请检查网络原因"};
        }
      });
    }else{
      next();
    }
  });
}
