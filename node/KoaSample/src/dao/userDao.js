const model = require('../db/model');

let
  Pet = model.Pet,
  User = model.User;

var getUsers = async (userId) => {
  let mysqlOptions = {
    sql: 'select * from person',
    args: [1]
  };
  var users = await Pet.findAll();
  if (users.length == 0) {
    return null;
  } else {
    return users;
  }
};

module.exports = {
  getUsers: getUsers
};
