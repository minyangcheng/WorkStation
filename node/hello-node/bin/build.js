const path = require('path');
const UglifyJSPlugin = require('uglifyjs-webpack-plugin');

let entryPath = path.resolve(__dirname, '../src/es6/main.js');

module.exports = {
  entry: entryPath,
  plugins: [
    new UglifyJSPlugin({
      uglifyOptions: {
        compress: {
          warnings: false
        }
      },
      sourceMap: true
    })
  ],
  output: {
    filename: 'bundle.js',
    path: path.resolve(__dirname, '../dist')
  }
};
