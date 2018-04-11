const path = require('path');
const webpack = require('webpack');
const middleware = require('webpack-dev-middleware');
const webpackConfig = require('./webpackFile');
const compiler = webpack(webpackConfig);
const express = require('express');
const app = express();

const instance = middleware(compiler, {publicPath: webpackConfig.output.publicPath});
instance.waitUntilValid(() => {
  console.log('Package is in a valid state');
});
app.use(instance);

app.use(express.static(path.resolve(__dirname, '../')));

app.listen(3000, () => console.log('Example app listening on port 3000!'))
