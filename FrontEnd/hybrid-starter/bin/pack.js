#!/usr/bin/env node

// console.log(process.argv);
var fs = require('fs');
var path = require('path');
var archiver = require('archiver');
var moment = require('moment');

var distPath = path.resolve(__dirname, '../dist');

var packagesPath = path.resolve(__dirname, '../packages');

if (!fs.existsSync(packagesPath)) {
  fs.mkdirSync(packagesPath);
}

var nowTime = moment().format('YYYY-MM-DD-HHmmss');
var version = process.env.npm_package_version;
var zipPath=packagesPath + '/bundle-' + version + '-' + nowTime + '.zip';

writeVersionInfo(distPath, 'config.json');
zipDir(distPath, zipPath);

function zipDir(intputPath, outputZipPath) {
  var output = fs.createWriteStream(outputZipPath);
  var archive = archiver('zip', {
    zlib: {level: 9}
  });
  output.on('close', function () {
    console.log(archive.pointer() + ' total bytes');
    console.log('archiver has been finalized and the output file descriptor has closed.');
  });
  output.on('end', function () {
    console.log('Data has been drained');
  });
  archive.on('error', function (err) {
    throw err;
  });
  archive.pipe(output);
  archive.directory(intputPath, false);
  archive.finalize();
}

function writeVersionInfo(dirPath, fileName) {
  var data = {jsVersion: version};
  data = JSON.stringify(data);
  var writerStream = fs.createWriteStream(path.resolve(dirPath, fileName));
  writerStream.write(data, 'UTF8');
  writerStream.end();
  writerStream.on('finish', function () {
    console.log(fileName + " create finish");
  });
  writerStream.on('error', function (err) {
    console.log(err.stack);
  });
}
