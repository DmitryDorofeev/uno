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
            window["VK"].Auth.login(_.bind(this.loginStatus, this));
            window["VK"].Auth.getLoginStatus(_.bind(this.loginStatus, this));
        },
        loginStatus: function (response) {
            if (response.session) {
                var id = response.session.mid;
                window["VK"].Api.call('users.get', {uids: id}, _.bind(function(r) {
                    if(r.response) {
                        var name = r.response[0].first_name + ' ' + r.response[0].last_name;
                        var api = new API();
                        api.send('post', '/api/v1/auth/signin', {token: id, name: name}).then(
                            _.bind(function (data) {
                                location.reload();
                            }, this),
                            _.bind(function () {
                                this.trigger('login:bad');
                            }, this)
                        );
                    }
                    else {
                        this.trigger('login:bad');
                    }
                }, this));
            }
            else {
                this.trigger('login:error');
            }
        }
    });

    return new UserModel();

});
