define([
    'backbone',
    'jquery',
    'models/user',
    'mockjax'
], function (Backbone, $, userModel) {
    var run = function () {
        $.mockjax({
            url: '/api/v1/auth/signin',
            type: 'POST',
            response: function(settings) {
                this.responseText = {
                    status: 200,
                    login: 'testLogin',
                    email: 'testEmail'
                };
            }
        });
        $.mockjax({
            url: '/api/v1/auth/signup',
            type: 'POST',
            response: function(settings) {
                this.responseText = {
                    status: 200
                };
            }
        });
        $.mockjax({
            url: '/api/v1/auth/logout',
            type: 'POST',
            response: function(settings) {
                this.responseText = {
                    status: 200
                };
            }
        });
        
        var listener = {};
        
        _.extend(listener, Backbone.Events);
        
        asyncTest('userModel.login', function () {
            userModel.login({login: 'testLogin', password: 'saaa'});
            listener.listenToOnce(userModel, 'login:ok', function () {
                start();
                ok(userModel.get('login') === 'testLogin', 'after login username puts to model');
                ok(userModel.get('password') === undefined, 'after login password field is empty');
            }); 
        });
        asyncTest('userModel.signup', function () {
            userModel.signup({login: 'pass', email: 'root@root.ru', password: 'saaa'});
            listener.listenToOnce(userModel, 'signup:ok', function () {
                start();
                ok(userModel.get('login') === undefined, 'after signup login field is empty');
                ok(userModel.get('password') === undefined, 'after signup password field is empty');
            });
        });
        asyncTest('userModel.logout', function () {
            userModel.logout();
            listener.listenTo(userModel, 'logout', function () {
                start();
                ok(userModel.get('login') === undefined, 'after logout login field is empty');
                ok(userModel.get('email') === undefined, 'after logout email field is empty');
                ok(userModel.get('password') === undefined, 'after logout password field is empty');
            });
        });
    };
    
    return {run: run};
});