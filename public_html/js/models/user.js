define([
    'jquery',
    'backbone',
    'userSync',
    'api'
], function ($, Backbone, userSync, API) {
    var UserModel = Backbone.Model.extend({
        initialize: function () {
            this.fetch();
        },
        sync: userSync,
        isLogined: function () {
            return (this.has('isLogined'));
        },
        logout: function () {
            this.save();
        },
        login: function (data) {
            this.set(data);
            this.save();
        },
        signup: function (data) {
            this.set(data);
            this.save();
        },
        vkLogin: function () {
            window["VK"].init({
                apiId: 4758906
            });
            window["VK"].Auth.getLoginStatus(this.loginStatus);
        },
        loginStatus: function (response) {
            if (response.session) {
                console.log(response);
                var id = response.session.mid;
                window["VK"].Api.call('users.get', {uids: id}, function(r) {
                    if(r.response) {
                        var name = r.response[0].first_name + ' ' + r.response[0].last_name;
                        var api = new API();
                        api.send('post', '/api/v1/auth/signin', {token: id, name: name}).then(
                            function (data) {
                                this.trigger('signup:ok');
                            },
                            function () {
                                this.trigger('signup:bad');
                            }
                        );
                    }
                });
            }
        }
    });

    return new UserModel();

});
