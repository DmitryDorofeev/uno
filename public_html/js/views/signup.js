define([
    'jquery',
    'backbone',
    'tmpl/all',
    'models/user'
], function ($, Backbone, template, userModel) {

    var SignupView = Backbone.View.extend({

        model: userModel,

        initialize: function () {
            this.listenTo(this.model, 'signup:bad', this.renderRegError);
            this.listenTo(this.model, 'signup:error', this.renderServerError);
        },

        events: {
            'submit #signup-form': 'signup'
        },

        template: function () {
            return template({ block: 'signup' });
        },

        render: function () {
            this.$el.html(this.template());
            return this;
        },

        signup: function (event) {
            event.preventDefault();

            var form = $('#signup-form');
            this.model.signup({
                login: form.find('input[name=login]').val(),
                email: form.find('input[name=email]').val(),
                password: form.find('input[name=password]').val()
            });
        },

        show: function () {
            this.trigger('show', this);
            this.$el.show();
        },

        hide: function () {
            this.$el.hide();
        },

        renderRegError: function (message) {
            this.trigger('error', message);
        },

        renderServerError: function () {
            this.trigger('error', 'Ошибка соединения с сервером');
        }

    });

    return SignupView;
});
