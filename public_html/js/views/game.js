define([
  'backbone',
  'tmpl/game',
  'models/user'
], function (Backbone, tmpl, userModel) {
  var GameView = Backbone.View.extend({
    model: userModel,
    events: {
      'click button': 'send'
    },
    initialize: function() {

    },
    template: function() {
      return tmpl();
    },
    render: function() {
      this.$el.html(this.template());
      return this;
    },
    show: function () {
      if (userModel.isLogined()) {
        this.trigger('show', this);
        this.$el.show();
        this.trigger('load:start', 'Ожидание игроков');
        this.model.set('inGame', true);
      }
      else {
        userModel.trigger('login:no');
      }
    },
    hide: function () {
        this.$el.hide();
        this.model.set('inGame', false);
    },
    send: function () {
        this.ws.send();
    },
    message: function (data) {
        console.log(data);
    }
  });

  return GameView;
});
