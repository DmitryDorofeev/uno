define([
    'jquery'
],function ($) {

    var ApiDfd,
        requestTimeout,
        sendUrl,
        sendMethod;

    var API = function (baseUrl) {
        this.baseUrl = baseUrl || '';
    }

    var _send = function (method, params) {
        $.ajax({
            type: method,
            url: sendUrl,
            data: params || {},
            dataType: 'json'
        }).done(function (data) {
            if (data.status === 100) {
                sendMethod = method;
            }
            else if (data.status === 200) {
                clearTimeout(requestTimeout);
                ApiDfd.resolve(data);
            }
            else {
                clearTimeout(requestTimeout);
                ApiDfd.reject(data);
            }
        }).fail(function () {
            clearTimeout(requestTimeout);
            ApiDfd.reject();
        });
    }

    API.prototype.send = function (method, url, params) {
        sendUrl = this.baseUrl + url;
        ApiDfd = new $.Deferred();
        _send(method, params);
        requestTimeout = setTimeout(_send, 2000);
        return ApiDfd.promise();
    }

    return API;
});