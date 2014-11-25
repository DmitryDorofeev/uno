define([
    'jquery',
    'backbone'
], function ($, Backbone) {
    return function(method, model, options) {
        var methodMap = {
            'create': {
                method: 'POST',
                url: '/api/v1/scoreboard/new',
                success: function (resp) {
                    if (resp.status == 200) {
                    }
                    else if (resp.status == 500) {
                    }
                },
                error: function () {
                    model.trigger('signup:error');
                }
            },
            'read': {
                method: 'GET',
                url: '/api/v1/scoreboard',
                success: function (resp) {
                    if (resp.status === 200) {
                        model.reset(resp);
                    }
                }
            },
            'update': {

            }
        };
        var type = methodMap[method].method,
            url = methodMap[method].url,
            success = methodMap[method].success,
            error = methodMap[method].error || function () {};

        var xhr = $.ajax({
            type: type,
            url: url,
            data: (model instanceof Backbone.Collection) ? model.toJSON() : {},
            dataType: 'json'
        }).done(success).fail(error);

        return xhr;
    }
});