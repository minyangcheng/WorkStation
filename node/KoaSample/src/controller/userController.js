const userService = require('./../service/userService.js');
const APIError = require('../middlewrae/rest').APIError;

var getUsers = async (ctx, next) => {
  let users = await userService.getUsers();
  if (users) {
    ctx.rest({users: users});
  } else {
    throw new APIError('product:not_found', 'product not found by id.');
  }
};

var saveUserinfo = (ctx, next) => {
  const requestString = ctx.data;
  Console.log(requestString);
};

module.exports = {
  'GET /api/getUsers': getUsers,
  'POST /api/saveUserinfo': saveUserinfo
};
