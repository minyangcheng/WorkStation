const fs = require('mz/fs');
const mime = require('mime');
const path = require('path');

var index = async (ctx, next) => {
  ctx.redirect('/static/index.html');
};

module.exports = {
  'GET /': index,
};
