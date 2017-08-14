var merge = require('webpack-merge')
var prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  API_SERVER_URL: '"http://10.10.13.12:8080"',
  SOURCE: '"123456"',
  SECRET: '"1234567890"'
})
