var mysql      = require('mysql');
var connection = mysql.createConnection({
    host     : '10.10.12.165',
    user     : 'root',
    password : '123',
    database : 'cg'
});
connection.connect();

function query(callback) {
    connection.query('select * from test',callback);
}

module.exports={query}

