var axios = require('axios');
var queryString = require('querystring');
var fs = require('fs');
var FormData = require('form-data');

var baseURL = 'http://localhost:8081';

var instance = axios.create();

var successHandler = response => {
  if (typeof response.data == 'object') {
    if (response.data.code == 10000) {
      return response.data;
    } else {
      return Promise.reject({message: response.data.message, code: response.data.code});
    }
  }
  return response.data;
};

var errorHandler = err => {
  if(err instanceof Error){
    return Promise.reject({message: '网络请求出错', code: -1, data:err});
  }else{
    return Promise.reject(err);
  }

};


var postConfig = {
  baseURL,
  timeout: 5000,
  transformRequest: [function (data) {
    return queryString.stringify(data);
  }],
  transformResponse: [function (data) {
    return JSON.parse(data);
  }],
  headers: {
    'user-agent': 'http-client',
    'Content-Type': 'application/x-www-form-urlencoded',
  },
};

module.exports = {

  post(url, params, config = postConfig) {
    return instance.post(url, params, postConfig)
      .then(successHandler)
      .catch(errorHandler);
  },

  post_(url, params, config = postConfig) {
    return instance.post(url, params, postConfig)
  },

  get(url, params) {
    return instance.get(url, params)
      .then(successHandler)
      .catch(errorHandler);
  },

  get_(url, params) {
    return instance.get(url, params);
  },

  uploadFile(url, filePath) {
    let data = fs.createReadStream(filePath);
    let form = new FormData({maxDataSize: 1024 * 1024 * 100});
    form.append('file', data);
    return instance.post(url, form, {
      headers: form.getHeaders(),
      timeout: 60000,
      baseURL,
      maxContentLength: 1024 * 1024 * 100
    }).then(successHandler)
      .catch(errorHandler);
  },

  uploadFile_(url, filePath) {
    let data = fs.createReadStream(filePath);
    let form = new FormData({maxDataSize: 1024 * 1024 * 100});
    form.append('file', data);
    return instance.post(url, form, {
      headers: form.getHeaders(),
      timeout: 60000,
      baseURL,
      maxContentLength: 1024 * 1024 * 100
    });
  }
};
