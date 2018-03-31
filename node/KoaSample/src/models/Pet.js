const db = require('../db/db');

module.exports = db.defineModel('pets', {
  ownerId: db.ID,
  name: db.STRING(100),
  gender: db.BOOLEAN,
  birth: db.STRING(10),
});
