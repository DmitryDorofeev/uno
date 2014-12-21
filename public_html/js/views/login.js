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
    },
    events: {
      'submit #signin-form': 'login'
    },
    template: function () {
      return tmpl();
    },
    render: function() {
      this.$el.html(this.template());
      return this;
    },
    login: function(event) {
      event.preventDefault();
      this.trigger('error:hide');
      var form = this.$('#signin-form');
      this.model.login({
        login: form.find('input[name=login]').val(),
        password: form.find('input[name=password]').val()
      });
    },
    renderServerError: function() {
      this.trigger('error', 'Ошибка соединения с сервером');
    },
    renderLoginError: function(message) {
      this.trigger('error', message);
    },
    show: function () {
      this.trigger('show', this);
      if (this.model.has('isLogined')) {
        this.model.trigger('login:ok');
      }
      else {
        this.$el.show();
      }
    },
    hide: function () {
        this.$el.hide();
    }
  });

  return LoginView;
});
