exports.warn = function (msg) {
  console.error(`[error]: ${JSON.stringify(msg)}`);
}

exports.log = function(msg) {
  console.log(`[log]: ${JSON.stringify(msg)}`);
}
