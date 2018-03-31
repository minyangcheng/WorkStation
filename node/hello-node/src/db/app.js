const model = require('./model');

let
  Pet = model.Pet,
  User = model.User;

(async () => {
  // var user = await User.create({
  //   name: 'John',
  //   gender: false,
  //   email: 'john-' + Date.now() + '@garfield.pet',
  //   passwd: 'hahaha'
  // });
  // console.log('created: ' + JSON.stringify(user));
  // var cat = await Pet.create({
  //   ownerId: user.id,
  //   name: 'Garfield',
  //   gender: false,
  //   birth: '2007-07-07',
  // });
  // console.log('created: ' + JSON.stringify(cat));
  // var dog = await Pet.create({
  //   ownerId: user.id,
  //   name: 'Odie',
  //   gender: false,
  //   birth: '2008-08-08',
  // });
  // console.log('created: ' + JSON.stringify(dog));

  // var result = await Pet.findAll();
  // result.forEach(value => console.log(JSON.stringify(value)))

  // var pets = await Pet.findAll({
  //   where: {
  //     name: 'Gaffey'
  //   }
  // });
  // console.log(`find ${pets.length} pets:`);
  // for (let p of pets) {
  //   console.log(JSON.stringify(p));
  // }

  var result = await Pet.findAll({where:{id:1}});
  result.forEach(async p => {
    p.gender=true;
    await p.save();
  });
  var pets = await Pet.findAll({where:{id:1}});
  for (let p of pets) {
    console.log(JSON.stringify(p));
  }
})();
