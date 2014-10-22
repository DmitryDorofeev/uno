define([
  'jquery',
  'backbone',
  'tmpl/signup',
  'models/user'
], function ($, Backbone, tmpl, userModel) {
  var SignupView = Backbone.View.extend({
    model: userModel,
    initialize: function() {
      this.listenTo(this.model, 'signup:bad', this.renderRegError);
      this.listenTo(this.model, 'signup:error', this.renderServerError);
      this.render();
    },
    events: {
      'submit #signup-form': 'signup'
    },
    template: tmpl,
    render: function() {
      this.$el.html(this.template());
      this.$error = this.$el.find('#error');
      return this;
    },
    signup: function (event) {
      event.preventDefault();
      var form = $('#signup-form');
      this.model.signup({
        login: form.find('input[name=login]').val(),
        email: form.find('input[name=email]').val(),
        password: form.find('input[name=password]').val(),
      });
    },
    renderError: function(msg) {
      this.$error.text(msg);
    },
    show: function () {
      this.trigger('show', this);
    },
    renderRegError: function () {
      this.$error.text('Пользователь с таким именем уже существует');
    },
    renderServerError: function () {
      this.$error.text('Ошибка соединения с сервером');
    }
  });

  return new SignupView();
});
