const mysql = require('../util/mysqlUtil.js');

var getUserById = async (userId) => {
  let mysqlOptions = {
    sql: 'select * from person',
    args: [1]
  };
  var users = await mysql.execQuery(mysqlOptions);
  if (users.length == 0) {
    return null;
  } else {
    return users;
  }
};

module.exports = {
  getUserById: getUserById
};
