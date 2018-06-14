var EventProxy = require('eventproxy');

var ep = EventProxy.create("template", "data", "l10n", function (template, data, l10n) {
  console.log(template)
  console.log(data)
  console.log(l10n)
});

setTimeout(function () {
  ep.emit("template",'template' );
},1000);
setTimeout(function () {
  // something
  ep.emit("data", 'data');
},4000);
setTimeout( function () {
  // something
  ep.emit("l10n", 'l10n');
},2000);
