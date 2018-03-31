const userDao = require('../dao/userDao.js');

var getUserById = async (userId) => {
  var users = await userDao.getUserById(userId);
  return users ? JSON.stringify(users) : null;
}

module.exports = {
  getUserById: getUserById
};
