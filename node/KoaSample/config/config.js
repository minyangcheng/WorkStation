var config;
if (process.env.NODE_ENV === 'production') {
  console.log("Load productionConfig...");
  config = {
    db: {
      host: '10.10.12.165',
      user: 'root',
      password: '123',
      database: 'cg'
    },
    view_path: "/dist"
  };
} else {
  console.log("Load localConfig...");
  config = {
    db: {
      host: '10.10.12.165',
      user: 'root',
      password: '123',
      database: 'cg'
    },
    view_path: "/views-ejs"
  };
}

module.exports = config;
