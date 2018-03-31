var config;
if (process.env.NODE_ENV === 'production') {
  config = {
    db: {
      dialect: 'mysql',
      database: 'cg',
      username: 'root',
      password: '123',
      host: 'localhost',
      port: 3306
    },
    view_path: "/dist"
  };
} else {
  config = {
    db: {
      dialect: 'mysql',
      database: 'cg',
      username: 'root',
      password: '123',
      host: 'localhost',
      port: 3306
    },
    view_path: "/views"
  };
}

module.exports = config;
