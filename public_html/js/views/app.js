define([
  'jquery',
  'backbone',
  'tmpl/app',
  'views/toolbar',
  'views/panel',
  'models/user',
  'views/game'
], function ($, Backbone, tmpl, toolbarView, panelView, userModel, gameView) {

  var AppView = Backbone.View.extend({
      tagName: 'div',
      className: 'app',
      model: userModel,
      initialize: function() {
        this.$container = $('body');
        this.render();
      },
      template: tmpl,
      render: function () {
        this.$el.html(this.template());
        this.$container.html(this.$el);
        this.$container.append(panelView.render().$el);
      },
      subscribe: function (views) {
        for (var i in views) {
          this.listenTo(views[i], 'show', this.add);
        }
      },
      unsubscribe: function (view) {
        this.stopListening(view);
      },
      add: function (view) {
        this.$el.find('#page').html(view.$el);
      }
  });

  return new AppView();

});
