const mysql = require('mysql');
const config = require('../../config/config.js');
var connectionPool;

function initConnectionPool() {
  if (connectionPool) {
    return;
  }
  connectionPool = mysql.createPool({
    'host': config.db.host,
    'port': config.db.port,
    'user': config.db.user,
    'password': config.db.password,
    'database': config.db.database
  });
};

var release = connection => {
  connection.end(function (error) {
    if (error) {
      console.log('Connection closed failed.');
    } else {
      console.log('Connection closed succeeded.');
    }
  });
};

var execQuery = sqlOptions => {
  return new Promise((resolve, reject) => {
    try {
      initConnectionPool();
    } catch (e) {
      reject(e);
      return;
    }
    connectionPool.getConnection((error, connection) => {
      if (error) {
        console.log("Get connection from mysql pool failed !");
        reject(error);
        return;
      }
      var sql = sqlOptions['sql'];
      var args = sqlOptions['args'];
      if (sql && !args) {
        var query = connection.query(sql, (error, results) => {
          if (error) {
            console.log('Execute query error !');
            reject(error);
          }
          resolve(results);
        });
      } else if (sql && args) {
        var query = connection.query(sql, args, function (error, results) {
          if (error) {
            console.log('Execute query error !');
            reject(error);
          }
          resolve(results);
        });
      }

      connection.release(function (error) {
        if (error) {
          console.log('Mysql connection close failed !');
          reject(error);
        }
      });
    });
  });
};

module.exports = {
  release: release,
  execQuery: execQuery
}
