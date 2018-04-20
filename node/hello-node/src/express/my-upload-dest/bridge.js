var callbackContainer = {};

var id = 0;

function generateCallbackId() {
  return ++id;
}

window.callbackHandler = function (params) {
  if (!params && !params.callbackId) {
    var callback = callbackContainer[params.callbackId];
    if (callback) {
      callback(params);
    }
  }
}

var mdsweb_=window.mdsweb;
window.mdsweb = {};
mdsweb.executeFunction = function (params, callback) {
  if (!params) {
    params = {};
  }
  if (!callback) {
    callback = function () {
    }
  }
  var callbackId = generateCallbackId();
  params.callbackId = callbackId;
  callbackContainer[callbackId] = callbackId;
  mdsweb_.executeFunction(params);
}
