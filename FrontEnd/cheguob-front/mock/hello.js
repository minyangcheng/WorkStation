module.exports = {
  api: '/api/hello',
  response: function (req, res) {
    res.send(JSON.stringify({username:'minyangchneg',password:'123456abc'}));
  }
}
