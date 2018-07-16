#!/usr/bin/env node

function fetchData() {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      // resolve('this is data');
      reject('this is err');
    }, 1000);
  })
}

Promise.resolve()
  .then(data=>{
    console.log(1);
    console.log(data);
  })
  .then(()=>fetchData())
  .then(data=>{
    console.log(2);
    console.log(data);
  })
  .catch(data=>{
    console.log(3);
    console.log(data);
  })
  .then(data=>{
    console.log(4);
    console.log(data)
  })
