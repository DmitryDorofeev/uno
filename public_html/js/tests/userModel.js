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
        
        module('User model tests');
        
        asyncTest('userModel.login', function () {
            userModel.login({login: 'testLogin', password: 'saaa'});
            listener.listenToOnce(userModel, 'login:ok', function () {
                start();
                ok(userModel.get('login') === 'testLogin', 'after login username puts to model');
                ok(!userModel.has('password'), 'after login password field is empty. Now "' + userModel.get('password') + '"');
                ok(userModel.has('isLogined'), 'if logined isLogined exists');
            }); 
        });
        asyncTest('userModel.signup', function () {
            userModel.signup({login: 'pass', email: 'root@root.ru', password: 'saaa'});
            listener.listenToOnce(userModel, 'signup:ok', function () {
                start();
                ok(!userModel.has('login'), 'after signup login field is empty');
                ok(!userModel.has('password'), 'after signup password field is empty.');
            });
        });
        asyncTest('userModel.logout', function () {
            userModel.logout();
            listener.listenTo(userModel, 'logout', function () {
                start();
                ok(!userModel.has('login'), 'after logout login field is empty');
                ok(!userModel.has('email'), 'after logout email field is empty');
                ok(!userModel.has('password'), 'after logout password field is empty');
            });
        });
    };
    
    return {run: run};
});