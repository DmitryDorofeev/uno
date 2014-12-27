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

	var listener = {};
        
        _.extend(listener, Backbone.Events);
        
        module('User model tests 2');
        
        asyncTest('userModel.login', function () {
            userModel.login({login: 'testLogin', password: 'saaa'});
            listener.listenToOnce(userModel, 'login:ok', function () {
                start();
                ok(userModel.get('login') === 'testLogin', 'after login username puts to model');
                ok(!userModel.has('password'), 'after login password field is empty. Now "' + userModel.get('password') + '"');
                ok(userModel.has('isLogined'), 'if logined isLogined exists');
            }); 
        });
    };

    return {run: run};
});