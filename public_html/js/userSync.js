define([
    'jquery',
	'backbone',
    'api'
], function($, Backbone, API) {

    var api = new API('/api/v1/auth/');

	return function(method, model, options) {

        if ((method === 'create') && !((model.has('email')) && (model.has('password')))) {
            method = 'update'
        }

        var methodMap = {
            'create': {
                send: function () {
                    return api.send('POST', 'signup', model.toJSON()).done(this.success).fail(this.error);
                },
                success: function (resp) {
                    if (resp.status == 200) {
                        model.clear();
                        model.trigger('signup:ok');
                    }
                    else if (resp.status == 500) {
                        model.trigger('signup:bad', resp.message);
                    }
                },
                error: function () {
                    model.trigger('signup:error');
                }
            },
            'read': {
                send: function () {
                    api.send('GET', 'profile', model.toJSON()).done(this.success).fail(this.error);
                },
                success: function (resp) {
                    if (resp.status === 200) {
                        model.set({
                            'login': resp.login,
                            'email': resp.email,
                            'isLogined': true
                        });
                        model.trigger('login:ok');
                    }
                }
            },
            'update': {
                send: function () {
                    if (model.has('password')) {
                        api.send('POST', 'signin', model.toJSON()).done(this.success).fail(this.error);
                    }
                    else {
                        api.send('POST', 'logout', model.toJSON()).done(this.success).fail(this.error);
                    }
                },
                success: function (resp) {
                    if (model.has('password')) {
                        if (resp.status === 200) {
                            model.set({
                                'login': resp.login,
                                'email': resp.email,
                                'isLogined': true
                            });
                            model.unset('password');
                            model.trigger('login:ok');
                        }
                        else if (resp.status === 500) {
                            model.trigger('login:bad', resp.message);
                        }
                    }
                    else {
                        model.clear();
                        model.trigger('logout');
                    }
                },
                error: function () {
                    if (model.has('password')) {
                        model.trigger('login:error');
                        model.unset('password')
                    }
                    else {
                        model.trigger('logout:error');
                    }
                }
            }
        };
        return methodMap[method].send();
	};
});
	