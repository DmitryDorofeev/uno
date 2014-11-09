define([
  'backbone',
  'tmpl/game',
  'models/user',
  'models/game',
  'views/gamesettings'
], function (Backbone, tmpl, userModel, gameModel, gameSettings) {
  var GameView = Backbone.View.extend({
    model: userModel,
    game: gameModel,
    initialize: function() {
      this.listenTo(this.game, 'game:settings', this.showSettings);
      this.listenTo(gameSettings, 'game:connect', this.sendSettings);
    },
    template: function() {
      return tmpl();
    },
    render: function() {
      this.$el.html(this.template());
      this.$el.append(gameSettings.$el);
      return this;
    },
    show: function () {
      if (userModel.isLogined()) {
        this.trigger('show', this);
        this.$el.show();
        this.model.set('inGame', true);
        this.showSettings();
      }
      else {
        userModel.trigger('login:no');
      }
    },
    hide: function () {
        this.$el.hide();
        this.model.set('inGame', false);
        this.game.close();
    },
    showSettings: function () {
      this.trigger('load:done');
      gameSettings.show();
    },
    sendSettings: function (val) {
      this.trigger('load:start', 'Подключение...');
      this.game.playersNumber = val;
      this.game.connect();
    }
  });

  return GameView;
});
