var path = require('path')
var webpack = require('webpack')
var ExtractTextPlugin = require('extract-text-webpack-plugin')
var HtmlWebpackPlugin = require('html-webpack-plugin')
var FriendlyErrorsWebpackPlugin = require('friendly-errors-webpack-plugin')
var merge = require('webpack-merge')
var ConsolePlugin = require('./ConsolePlugin.js')
const CleanWebpackPlugin = require('clean-webpack-plugin')

function resolve(dir) {
  return path.join(__dirname, '../' + dir)
}

module.exports = {
  entry: {
    'page/main': [resolve('src/page/test.js'),resolve('src/page/main.js')],
  },
  output: {
    filename: '[name].js',
    path: resolve('dist'),
    publicPath: ''
  },
  resolve: {
    extensions: ['.js', '.json'],
    alias: {}
  },
  module: {
    rules: [
      {
        test: /main\.js$/,
        use: [
          {
            loader: 'custom-loader',
            options: {
              name: 'minych',
            }
          }
        ]
      },
      {
        test: /\.js$/,
        use: [
          'babel-loader',
        ],
        include: [resolve('src')],
        exclude: [resolve('node_modules')]
      },
      {
        test: /\.css$/,
        use: ['style-loader', 'css-loader',]
      },
      {
        test: /\.(png|jpg|gif)$/,
        use: [
          {
            loader: 'file-loader',
            options: {
              name: '[name].[ext]',
              outputPath: 'assets',
              publicPath: '../assets',
            }
          }
        ]
      }
    ]
  },
  plugins: [
    new CleanWebpackPlugin('dist', {root: resolve('')}),
    new webpack.DefinePlugin({
      'process.env.NODE_ENV': "'" + process.env.NODE_ENV + "'",
    }),
    new HtmlWebpackPlugin({template: resolve('index.html'), hash: true}),
    // new ConsolePlugin()
  ]
}
