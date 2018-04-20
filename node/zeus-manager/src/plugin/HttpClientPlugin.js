var axios = require('axios');
var qs = require('qs');

var baseURL = 'http://localhost:8081';

var instance = axios.create();

var successHandler = response => {
  if (typeof response.data == 'object') {
    if (response.data.code == 10000) {
      return response.data;
    } else {
      var err = new Error(response.data.message);
      err.code = response.data.code;
      throw err;
    }
  }
  return response.data;
};

var errorHandler = err => {
  var resErr = new Error(err.toString());
  resErr.code = -1;
  throw resErr;
};


var postConfig = {
  baseURL,
  timeout: 5000,
  transformRequest: [function (data) {
    return qs.stringify(data);
  }],
  transformResponse: [function (data) {
    return JSON.parse(data);
  }],
  // headers: {'user-agent': 'http-client'},
};

var httpClient = {

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

  uploadFile(url, file) {
    let form = new FormData();
    form.append('file', file);
    return instance.post(url, form, {
      timeout: 20000,
      baseURL,
      maxContentLength: 1024 * 1024 * 100
    }).then(successHandler)
      .catch(errorHandler);
  },

  uploadFile_(url, file) {
    let form = new FormData();
    form.append('file', file);
    return instance.post(url, form, {
      timeout: 20000,
      baseURL,
      maxContentLength: 1024 * 1024 * 100
    });
  }
};

export default {
  install(Vue, options) {
    Vue.prototype.$http = httpClient;
  },
  ...httpClient
}
