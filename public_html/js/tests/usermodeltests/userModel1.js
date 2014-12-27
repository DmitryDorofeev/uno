define([
    'backbone',
    'jquery',
    'models/user',
    'mockjax'
], function (Backbone, $, userModel) {
    var run = function () {
    	$.mockjax({
            url: '/api/v1/auth/signup',
            type: 'POST',
            response: function(settings) {
                this.responseText = {
                    status: 200
                };
            }
        });

    var listener = {};
        
        _.extend(listener, Backbone.Events);
        
        module('User model tests 1');

        asyncTest('userModel.signup', function () {
            userModel.signup({login: 'pass', email: 'root@root.ru', password: 'saaa'});
            listener.listenToOnce(userModel, 'signup:ok', function () {
                start();
                ok(!userModel.has('login'), 'after signup login field is empty');
                ok(!userModel.has('password'), 'after signup password field is empty.');
            });
        });

    };

    return {run: run};
});