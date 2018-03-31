const db = require('../config/db');
const Sequelize = require('sequelize');

var user = db.define('user', {
  id: {
    type: Sequelize.STRING(50),
    primaryKey: true
  },
  userId: Sequelize.STRING(100),
  userName: Sequelize.STRING(50),
  createdAt: Sequelize.BIGINT,
  updatedAt: Sequelize.BIGINT
}, {
  timestamps: false
});
module.exports = user;
