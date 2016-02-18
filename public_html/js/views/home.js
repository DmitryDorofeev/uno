define([
    'jquery',
    'backbone',
    'tmpl/all',
    'models/user'
], function ($, Backbone, template, userModel) {

    var HomeView = Backbone.View.extend({
        model: userModel,
        initialize: function () {
            this.listenTo(this.model, 'change', this.render);
        },
        events: {
            'click .js-logout': 'logout',
            'click .js-vk-login': 'vkLogin'
        },
        template: function () {
            return template({
                block: 'main',
                user: this.model
            });
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
        vkLogin: function () {
            this.model.vkLogin();
        }
    });

    return HomeView;

});
