define([
    'backbone',
    'legacy/tmpl/login',
    'models/user'
], function (Backbone, tmpl, userModel) {
    var LoginView = Backbone.View.extend({
        model: userModel,
        initialize: function () {

        },
        template: function () {
            return tmpl();
        },
        render: function () {
            this.$el.html(this.template());
            return this;
        },
        events: {
            'submit #login-form': 'login'
        },
        login: function (event) {
            event.preventDefault();
            var login = this.$('input[name=login]').val(),
                password = this.$('input[name=password]').val();
            this.model.set('login', login);
            this.model.set('password', password);
            this.model.login();
        }
    });

    return new LoginView();
});