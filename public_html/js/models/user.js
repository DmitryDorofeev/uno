define([
    'jquery',
    'backbone',
    'userSync'
], function ($, Backbone, userSync) {
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

            }
        }
    });

    return new UserModel();

});
