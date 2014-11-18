define([
  'backbone',
  'tmpl/game',
  'models/user',
  'models/game',
  'views/gamesettings',
  'views/players',
  'views/cards'
], function (Backbone, tmpl, userModel, gameModel, gameSettings, playersView, cardsView) {
  var GameView = Backbone.View.extend({
    model: userModel,
    game: gameModel,
    initialize: function() {
        this.listenTo(this.game, 'load:start', this.loadStart);
        this.listenTo(this.game, 'load:done', this.loadDone);
        this.listenTo(this.game, 'game:settings', this.showSettings);
        this.listenTo(gameSettings, 'game:connect', this.sendSettings);
        this.listenTo(playersView, 'show', this.showView);
        this.listenTo(this.game, 'cards:render', this.renderCards);
    },
    loadStart: function (msg) {
        this.trigger('load:start', msg);
    },
    loadDone: function () {
        this.trigger('load:done');
    },
    template: function () {
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
      gameSettings.show();
    },
    sendSettings: function (val) {
      this.game.players = val;
      this.game.connect();
    },
    showView: function (view) {
        this.$el.append(view.$el);
    },
    renderCards: function () {
      this.$el.append(cardsView.render().$el);
    }
  });

  return GameView;
});
