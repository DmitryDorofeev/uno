define([
  'backbone',
  'tmpl/game',
  'models/user',
  'views/stage',
  'views/ship',
  'views/sun',
  'motion'
], function (Backbone, tmpl, userModel, stageView, shipView, sunView) {
  var GameView = Backbone.View.extend({
    className: 'game',
    id: 'game',
    model: userModel,
    events: {
      'click': 'moveShip'
    },
    initialize: function() {
      this.render();
    },
    template: function() {
      return tmpl();
    },
    render: function() {
      this.$el.html(this.template());
      this.$el.append(stageView.render().$el);
      this.$el.append(sunView.render().$el);
      this.$el.append(shipView.render().$el);
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
    }
  });

  return new GameView();
});
