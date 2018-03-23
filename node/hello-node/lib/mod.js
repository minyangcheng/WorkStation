"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});
function C() {
    this.sum = 0;
    this.add = function () {
        this.sum += 1;
    };
    this.show = function () {
        console.log(this.sum);
    };
}

var c = exports.c = new C();