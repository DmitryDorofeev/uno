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
      /*
this.ws = new WebSocket('ws://127.0.0.1:8080/gameplay');
      this.ws.onmessage = this.message;
*/
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
        this.$el.show();
        this.model.set('inGame', true);
      }
      else {
        userModel.trigger('login:no');
      }
    },
    hide: function () {
        this.$el.hide();
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
