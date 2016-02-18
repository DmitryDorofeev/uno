define([
  'jquery',
  'backbone',
  'tmpl/all',
  'models/user'
], function ($, Backbone, template, userModel) {
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
      return template({ block: 'login' });
    },
    render: function() {
      this.$el.html(this.template());

      return this;
    },
    login: function(event) {
        var form = this.$('#signin-form');
        event.preventDefault();

        this.trigger('error:hide');

        this.model.login({
            login: form.find('input[name=login]').val(),
            password: form.find('input[name=password]').val()
        });
    },
    renderServerError: function() {
      Backbone.Events.trigger('error', 'Ошибка соединения с сервером');
    },
    renderLoginError: function(message) {
      Backbone.Events.trigger('error', message);
    },
    show: function () {
      this.model.fetch();
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
