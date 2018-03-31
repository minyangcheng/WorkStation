var debug = require('debug');
var debugLevel = debug('app:debug');
var debugError = debug('app:error');

module.exports = {
  debug(msg, ...args) {
    debugLevel(msg, args);
  },
  error(msg, args) {
    debugError(msg, args);
  }
}
