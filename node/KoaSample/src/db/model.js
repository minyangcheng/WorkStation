const fs = require('fs');
const db = require('./db');
const path = require('path');

let modelDir = path.join(__dirname, '../models');

let files = fs.readdirSync(modelDir);

let js_files = files.filter((f) => {
  return f.endsWith('.js');
}, files);

module.exports = {};

for (let f of js_files) {
  console.log(`import model from file ${f}...`);
  let name = f.substring(0, f.length - 3);
  module.exports[name] = require(path.join(modelDir,f));
}

module.exports.sync = () => {
  db.sync();
};
