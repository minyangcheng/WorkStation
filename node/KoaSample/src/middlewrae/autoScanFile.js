const path = require('path');
const mime = require('mime');
const fs = require('mz/fs');

function staticFiles(prefix, dir) {
  return async (ctx, next) => {
    let urlPath = ctx.request.path;
    if (urlPath.startsWith(prefix)) {
      let fp = path.join(dir, urlPath.substring(prefix.length));
      if (await fs.exists(fp)) {
        ctx.response.type = mime.getType(urlPath);
        ctx.response.body = await fs.readFile(fp);
      } else {
        ctx.response.status = 404;
      }
    } else {
      await next();
    }
  };
}

module.exports = staticFiles;
