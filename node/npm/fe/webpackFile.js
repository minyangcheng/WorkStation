var path = require('path')

module.exports = {
    entry: {
        main: path.resolve(__dirname, 'index.js'),
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
                use: ['babel-loader','comment-require-loader']
            },
            {
                test: /\.css$/,
                use: ['style-loader','css-loader'],
            }
        ]
    },
    plugins: [],
    resolveLoader:{
        modules:['node_modules',path.resolve(__dirname,'../')]
    }
}
