const fs = require('fs');
const router = require('koa-router')();
const logger = require('./util/logUtil');

function addMapping(mapping) {
    for (var url in mapping) {
        var tempArr = url.split(' ');
        if (!tempArr || tempArr.length != 2) {
            return;
        }
        var method = tempArr[0];
        var path = tempArr[1];
        switch (method) {
            case 'GET':
                router.get(path, mapping[url]);
                logger.debug('register URL mapping: POST %s', path);
                break;
            case 'POST':
                router.post(path, mapping[url]);
                logger.debug('register URL mapping: POST %s', path);
                break;
            case 'PUT':
                router.put(path, mapping[url]);
                logger.debug('register URL mapping: PUT %s', path);
                break;
            case 'DELETE':
                router.del(path, mapping[url]);
                logger.deb
                ug('register URL mapping: DELETE %s', path);
                break;
            default:
                logger.debug('invalid URL: %s', url);
                break;
        }
    }
}

function addControllers(dir) {
    fs.readdirSync(__dirname + '/' + dir)
        .filter((f) => {
            return f.endsWith('.js');
        })
        .forEach((f) => {
            logger.debug(`find controller files: %s...`, f);
            let mapping = require(__dirname + '/' + dir + '/' + f);
            addMapping(mapping);
        });
}

module.exports = function (dir) {
    var controllersDir = dir || 'controller';
    addControllers(controllersDir);
    return router;
};
