var debug = require('./util/debug');
var log = debug.log;

module.exports = function ioserverMixmin(io) {
  // 连接的客户端数
  let userCountOnline = 0;
  // 连接的用户
  const usersOnline = {};

  function broadcast(socket, message) {
    // 向其他用户发送消息
    socket.broadcast.emit('broadcast', message);
  }

  function encodeNickName(nickName) {
    return encodeURIComponent(nickName);
  }

  function decodeNickName(nickName) {
    return decodeURIComponent(nickName);
  }

  io.on('connection', (socket) => {
    log('一个客户端连接');

    // 通知客户端已经连接
    socket.emit('open', {
      time: (new Date()).toLocaleString(),
    });

    socket.on('login', (nickName, fn) => {
      const res = {};
      const name = encodeNickName(nickName);

      if (usersOnline[name]) {
        res.code = 0;
        res.message = '昵称重复';

        fn(res);

        return;
      }

      const client = {
        name,
        socket,
      };

      const assignSocket = socket;

      assignSocket.name = name;
      usersOnline[name] = client;
      userCountOnline++;
      log(`${nickName}登陆`);

      res.code = 1;
      res.message = `当前用户数:${userCountOnline}`;

      fn(res);

      broadcast(socket, `欢迎${nickName}登陆成功,当前用户数:${userCountOnline}`);
    });

    socket.on('group chat message server', (message) => {
      log(`一个群聊信息:${message}`);

      const name = socket.name;

      // 发送给自己的消息
      socket.emit('group chat message client', {
        message,
        sender: decodeNickName(name),
      });

      socket.broadcast.emit('group chat message client', {
        message,
        sender: decodeNickName(name),
      });
    });

    socket.on('private chat message server', (data, fn) => {
      log(`一个私聊信息:${JSON.stringify(data)}`);

      const receiver = encodeNickName(data.receiver);
      const name = socket.name;
      const res = {};

      if (usersOnline[receiver]) {
        const targetSocket = usersOnline[receiver].socket;

        // 发送给私聊对象
        targetSocket.emit('private chat message client', {
          message: data.message,
          sender: decodeNickName(name),
        });

        res.code = 1;
        res.message = '私聊成功';
        fn(res);
      } else {
        // 私聊对象不存在
        res.code = 0;
        res.message = '私聊对象未上线';
        fn(res);
      }
    });

    socket.on('disconnect', () => {
      // 失联
      const name = socket.name;

      if (usersOnline[name]) {
        delete usersOnline[name];
        userCountOnline--;
        log(`${decodeNickName(name)}退出登陆`);

        broadcast(socket, `${decodeNickName(name)}退出登陆,当前用户数:${userCountOnline}`);
      }

      log('一个客户端失联');
    });
  });
}
