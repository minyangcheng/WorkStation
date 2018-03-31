const userDao = require('../dao/userDao.js');

var getUsers = async (userId) => {
  var users = await userDao.getUsers(userId);
  return users ? users : null;
}

module.exports = {
  getUsers: getUsers
};
