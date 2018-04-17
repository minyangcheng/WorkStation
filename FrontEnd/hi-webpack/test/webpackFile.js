var path = require('path')

module.exports = {
  entry: {
    main: path.resolve(__dirname, 'main.js'),
  },
  output: {
    filename: 'bundle.js',
    path: path.resolve(__dirname, 'dist'),
    publicPath: ''
  },
  resolve: {
    extensions: ['.js', '.json'],
    alias: {}
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        use:['babel-loader']
      }
    ]
  },
  plugins: []
}
