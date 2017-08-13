## 构建一个用webpack打包的前端项目

目前github上面的大部分前端开源项目都是用到了webpack打包工具，本文将讨论从零开始构建一个用webpack打包的基础前端项目。

### 项目构建基本步骤

#### npm生成package.json配置文件

在项目的根目录下执行`npm init`，然后一路回车即可创建一个可用npm管理包的项目。

```
{
  "name": "hi-webpack",
  "version": "1.0.0",
  "description": "",
  "main": "index.js",
  "scripts": {
    "dev": "cross-env NODE_ENV=development webpack-dev-server --inline --hot",
    "build": "cross-env NODE_ENV=production webpack --progress --hide-modules"
  },
  "author": "",
  "license": "ISC",
  "devDependencies": {
    "babel-core": "^6.0.0",
    "babel-loader": "^6.0.0",
    "babel-preset-es2015": "^6.0.0",
    "cross-env": "^5.0.0",
    "css-loader": "^0.28.3",
    "extract-text-webpack-plugin": "^2.1.0",
    "friendly-errors-webpack-plugin": "^1.6.1",
    "html-webpack-plugin": "^2.28.0",
    "style-loader": "^0.18.1",
    "webpack": "^2.6.1",
    "webpack-dev-server": "^2.4.5",
    "webpack-merge": "^4.1.0"
  },
  "dependencies": {
    "zeptojs": "^1.1.4"
  }
}
```

#### 添加editorconfig文件

在项目根目录下创建editorconfig文件，用于同一开发时代码格式。

```
root = true

[*]
charset = utf-8
indent_style = space
indent_size = 2
end_of_line = lf
insert_final_newline = true
trim_trailing_whitespace = true
```

#### 添加index.html文件

在项目根目录下创建index.html文件，用于作为webpack插件html-webpack-plugin的模板

```
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
  <title>webpack 2 demo</title>
</head>
<body>
</body>
</html>
```

#### 添加.babelrc文件

在项目根目录下创建.babelrc文件，用于作为webpack插件babel-loader的配置文件，可以让项目支持书写es6语法

```
{
  "presets": ["es2015"]
}
```

#### 添加webpack.config.js文件

webpack打包时会默认寻找根目录下的webpack.config.js文件，然后用此文件的配置去将src目录下的文件打包到dist中。

此配置说明：
1. 将css文件样式打包到独立的文件中去
2. 将项目中用到的公共代码打包到vendor.js（如果此代码是引用于node_modules库中）或manifest.js（如果此代码是自己项目中的）中，其他代码打包到main.js中
3. 压缩打包出来的文件
4. 通过`cross-env NODE_ENV=development webpack-dev-server --inline --hot`中设置的`NODE_ENV`，在配置文件中通过`process.env.NODE_ENV `区分生成环境或开发环境
5. 通过`devServer`来配置开发环境下的webpack-dev-server

*配置中用的一些loader和plugin请自行查阅*

```
var path = require('path')
var webpack = require('webpack')
//提取css文件样式到独立的文件中
var ExtractTextPlugin = require('extract-text-webpack-plugin')
//利用模板创建一个html文件，并关联上打包生成的js、css文件
var HtmlWebpackPlugin = require('html-webpack-plugin')
//错误提示处理插件
var FriendlyErrorsWebpackPlugin = require('friendly-errors-webpack-plugin')
//合并两个webpack配置的工具
var merge = require('webpack-merge')

function resolve(dir) {
  return path.join(__dirname, '..', dir)
}

var baseConfig = {
  entry: {
    main: './src/main.js',
  },
  output: {
    filename: '[name].js',
    path: path.resolve(__dirname, 'dist'),
  },
  resolve: {
    extensions: ['.js', '.json'],
    // alias: {}
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
    new HtmlWebpackPlugin({ template: './index.html', hash: true }),
  ]
}

var outputConfig

//根据运行环境参数，在基础配置对象上添加一些配置
if (process.env.NODE_ENV == 'development') {
  outputConfig = merge(baseConfig, {
    devServer: {
      quiet: true,
      port: 8081,
      host: 'localhost',
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

module.exports = outputConfig
```


### 目录结构

我们看看此最终的项目目录结构。

```
│  .babelrc  //babel-loader的配置文件
│  .editorconfig  //开发工具代码格式配置文件
│  index.html  //HtmlWebpackPlugin插件的模板文件
│  package.json  //npm包配置文件
│  webpack.config.js  //webpack配置文件
│
├─dist  //webpack生产环境下打包输出文件夹
└─src  //项目文件夹
        addItemOne.js  //普通js文件
        addItemTwo.js
        common.js
        main.js
        style.css  //样式文件
```

### 参考

1. <http://www.css88.com/doc/webpack2/guides/development/>
2. <https://www.npmjs.com/>
3. <https://segmentfault.com/a/1190000005742122#articleHeader1>
