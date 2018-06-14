#!/usr/bin/env node
var program = require('commander');
program
  .version('0.0.1')
  .option('-r, --resume', '简历')
  .option('-l, --language <lang>', '这个语言是我擅长的语言。')
  .option('-d, --database [db]>', 'this is test。')
  .parse(process.argv);

if (program.resume) {
  console.log('简历'
    + '-'
    + '这个是我的简历！'
  );
}

if (program.language) console.log('language: 我擅长的语言`' + program.language + '`');
if (program.database) console.log('db: 我擅长的语言`' + program.database + '`');
