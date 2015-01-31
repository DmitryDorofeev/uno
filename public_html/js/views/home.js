define([
    'jquery',
    'backbone',
    'tmpl/main',
    'models/user'
], function ($, Backbone, tmpl, userModel) {

    var HomeView = Backbone.View.extend({
        model: userModel,
        initialize: function () {
            this.listenTo(this.model, 'change', this.render);
        },
        events: {
            'click .js-logout': 'logout',
            'click #login_button': 'vk_login'
        },
        template: function () {
            return tmpl(this.model);
        },
        render: function () {
            this.$el.html(this.template());
            return this;
        },
        show: function () {
            this.trigger('show', this);
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        },
        logout: function (event) {
            event.preventDefault();
            this.model.logout();
        },
        vk_login: function () {
            window["VK"].init({
                apiId: 4758906
            });
            window["VK"].getLoginStatus(this.login_status);
        },
        login_status: function (response) {
            if (response.session) {
                alert('user: '+response.session.mid);
            }
        }
    });

    return HomeView;

});
