'use strict';

let couponApi = (() => {

    const _BASE_PREFIX = '/api/coupon/';

    function _findAll(param, callback) {
        $.ajax({
            url: _BASE_PREFIX,
            method: 'get',
            data: param,
            contentType: "application/json",
            dataType: 'json'
        }).then((response) => {
            callback(null, response);
        }).catch((response) => {
            let responseJSON = response.responseJSON;
            let message = responseJSON.message;
            callback(message, responseJSON);
        });
    }

    function _create(param, callback) {
        $.ajax({
            url: _BASE_PREFIX,
            method: 'post',
            data: JSON.stringify(param),
            contentType: "application/json",
            dataType: 'json'
        }).then((response) => {
            callback(null, response);
        }).catch((response) => {
            let responseJSON = response.responseJSON;
            let message = responseJSON.message;
            callback(message, responseJSON);
        });
    }

    return {
        findAll: _findAll,
        create: _create
    }
})();

