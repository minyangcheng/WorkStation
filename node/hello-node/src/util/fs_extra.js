const fs = require('fs-extra')

fs.move('./a','./b',{overwrite:true})
  .then(() => console.log('success!'))
  .catch(err => console.error(err))
