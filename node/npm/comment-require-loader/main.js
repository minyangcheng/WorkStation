#!/usr/bin/env node
module.exports=function (source) {
    var content= "require('./common.css') \n"+source;
    console.log(content)
}
