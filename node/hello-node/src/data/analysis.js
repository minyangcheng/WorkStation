var fs = require('fs');

var personContainer = [];

var input = fs.createReadStream(__dirname + '/person.txt');
readLines(input, (line) => {
  var arr = line.split(' ');
  personContainer.push({name: arr[0], age: arr[1]});
}, () => {
  sortPerson();
  writePerson();
});

function sortPerson() {
  personContainer.sort((p1,p2)=>{
    return p1.age-p2.age;
  })
}

function writePerson() {
  var str='';
  personContainer.forEach(p=>{
    str=str+p.name+' '+p.age+'\n';
  })
  fs.writeFileSync(__dirname + '/person_1.txt',str)
}

function readLines(input, lineCallback, completeCallback) {
  var remaining = '';
  input.on('data', function (data) {
    remaining += data;
    var index = remaining.indexOf('\n');
    while (index > -1) {
      var line = remaining.substring(0, index);
      remaining = remaining.substring(index + 1);
      lineCallback(line);
      index = remaining.indexOf('\n');
    }

  });

  input.on('end', function () {
    if (remaining.length > 0) {
      lineCallback(remaining);
    }
    completeCallback();
  });
}
