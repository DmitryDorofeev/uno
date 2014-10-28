define([
  'jquery',
  'backbone',
  'tmpl/login',
  'models/user'
], function ($, Backbone, tmpl, userModel) {
  var LoginView = Backbone.View.extend({
    model: userModel,
    initialize: function() {
      this.listenTo(this.model, 'login:error', this.renderServerError);
      this.listenTo(this.model, 'login:bad', this.renderLoginError);
      this.render();
    },
    events: {
      'submit #login-form': 'login'
    },
    template: tmpl,
    render: function() {
      this.$el.html(this.template());
      this.$error = this.$el.find('#error');
      return this;
    },
    login: function(event) {
      event.preventDefault();
      var form = this.$el.find('#login-form');
      this.model.login({
        login: form.find('input[name=login]').val(),
        password: form.find('input[name=password]').val()
      });
    },
    renderServerError: function() {
      this.$error.text('Ошибка соединения с сервером');
    },
    renderLoginError: function(message) {
      this.$error.text(message);
    },
    show: function () {
      this.trigger('show', this);
    },
    hide: function () {
        this.$el.hide();
    },
  });

  return new LoginView();
});
