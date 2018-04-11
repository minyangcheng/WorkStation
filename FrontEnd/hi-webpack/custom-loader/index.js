var loaderUtils=require('loader-utils');

module.exports = function (content) {
  content = content.replace(/var/g, 'let')
  console.log(loaderUtils.getOptions(this))
  return content;
};
