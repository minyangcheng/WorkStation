const userService = require('./../service/userService.js');

var getUserinfo = async (ctx, next) => {
  let query = ctx.query;
  let userId = query.id;
  let userInfo = await userService.getUserById(userId);
  if (userInfo) {
    ctx.response.type = 'json';
    ctx.response.body = userInfo;
  } else {
    var error = new Error();
    error.body = {code: 123, message: 'sorry can find useinfo'};
    throw error;
  }
};

var saveUserinfo = (ctx, next) => {
  const requestString = ctx.data;
  Console.log(requestString);
};

module.exports = {
  'GET /getUserinfo': getUserinfo,
  'POST /saveUserinfo': saveUserinfo
};
