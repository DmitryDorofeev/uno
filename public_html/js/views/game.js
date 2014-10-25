define([
  'backbone',
  'tmpl/game',
  'models/user'
], function (Backbone, tmpl, userModel) {
  var GameView = Backbone.View.extend({
    className: 'game',
    id: 'game',
    model: userModel,
    events: {
      'click button': 'send'
    },
    initialize: function() {
      this.ws = new WebSocket('ws://127.0.0.1:8080/gameplay');
      this.ws.onmessage = this.message;
      this.render();
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
        this.model.set('inGame', true);
      }
      else {
        userModel.trigger('login:bad');
      }
    },
    send: function () {
        this.ws.send();
    },
    message: function (data) {
        console.log(data);
    }
  });

  return new GameView();
});
