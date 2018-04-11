var path = require('path')
var webpack = require('webpack')
var ExtractTextPlugin = require('extract-text-webpack-plugin')
var HtmlWebpackPlugin = require('html-webpack-plugin')
var FriendlyErrorsWebpackPlugin = require('friendly-errors-webpack-plugin')
var merge = require('webpack-merge')

function resolve(dir) {
  return path.join(__dirname, dir)
}

var baseConfig = {
  entry: {
    main: './src/main.js',
  },
  output: {
    filename: '[name].js',
    path: path.resolve(__dirname, 'dist'),
    publicPath:'/dist'
  },
  resolve: {
    extensions: ['.js', '.json'],
    alias: {}
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        loader: 'babel-loader',
        include: [resolve('src'), resolve('test')]
      },
      {
        test: /\.css$/,
        use: ExtractTextPlugin.extract({
          fallback: "style-loader",
          use: "css-loader"
        }),
      },
    ]
  },
  plugins: [
    new webpack.DefinePlugin({
      'process.env.NODE_ENV': "'" + process.env.NODE_ENV + "'",
    }),
    new ExtractTextPlugin('styles.css'),
    new HtmlWebpackPlugin({template: './index.html', hash: true}),
  ]
}

var outputConfig

if (process.env.NODE_ENV == 'development') {
  outputConfig = merge(baseConfig, {
    devServer: {
      quiet: true,
      port: 8081,
      host: '0.0.0.0',
      historyApiFallback: true,
      noInfo: false,
    },
    devtool: '#cheap-module-eval-source-map',
    plugins: [
      new FriendlyErrorsWebpackPlugin(),
    ]
  })
}

if (process.env.NODE_ENV == 'production') {
  outputConfig = merge(baseConfig, {
    devtool: '#source-map',
    plugins: [
      new webpack.LoaderOptionsPlugin({
        minimize: true,
        debug: false
      }),
      // split vendor js into its own file
      new webpack.optimize.CommonsChunkPlugin({
        name: 'vendor',
        minChunks: function (module) {
          // 该配置假定你引入的 vendor 存在于 node_modules 目录中
          return module.context && module.context.indexOf('node_modules') !== -1;
        }
      }),
      // extract webpack runtime and module manifest to its own file in order to
      // prevent vendor hash from being updated whenever app bundle is updated
      new webpack.optimize.CommonsChunkPlugin({
        name: 'manifest',
        chunks: ['vendor']
      }),
    ]
  })
}

module.exports = outputConfig?outputConfig:baseConfig
