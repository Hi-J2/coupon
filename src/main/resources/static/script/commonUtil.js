'use strict';
let commonUtil = {
    checkEmail: function (email) {
        let regExp = /[0-9a-zA-Z][_0-9a-zA-Z-]*@[_0-9a-zA-Z-]+(\.[_0-9a-zA-Z-]+){1,2}$/;

        if (typeof email == 'undefined' || email.length == 0 || !email.match(regExp)) {
            return false;
        }

        return true;
    }

};