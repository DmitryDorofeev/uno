define([
    'jquery',
    'backbone',
    'tmpl/game',
    'models/user',
    'models/game',
    'views/gamesettings',
    'views/players',
    'views/cards',
    'views/deck'
], function ($, Backbone, tmpl, userModel, gameModel, gameSettings, playersView, cardsView, deckView) {
  var GameView = Backbone.View.extend({
    model: userModel,
    game: gameModel,
    settings: gameSettings,
    initialize: function() {
        this.views = {};
        this.register('settings', gameSettings);
        this.register('players', playersView);
        this.register('cards', cardsView);
        this.register('deck', deckView);
        this.listenTo(this.game, 'load:start', this.loadStart);
        this.listenTo(this.game, 'load:done', this.loadDone);
        this.listenTo(this.game, 'game:settings', this.showSettings);
        this.listenTo(this.settings, 'game:connect', this.sendSettings);
        this.listenTo(this.game, 'cards:render', this.renderCards);
    },
    register: function (name, view) {
        this.views[name] = view;
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
        this.$game = this.$('.game');
        _.forEach(this.views, function(view) {
            this.$game.append(view.render().$el);
        }, this);
        return this;
    },
    show: function () {
      if (userModel.isLogined()) {
          this.trigger('show', this);
          this.$el.show();
          if (this.game.connection == undefined) {
              this.showSettings();
          }
      }
      else {
        userModel.trigger('login:no');
      }
    },
    hide: function () {
        this.$el.hide();
        this.model.set('inGame', false);
    },
    showSettings: function () {
      gameSettings.show();
    },
    sendSettings: function (val) {
        console.log(val);
      this.game.players = val;
      this.game.connect();
    },
    renderCards: function () {
        this.views.cards.render();
        this.views.deck.show();
    }
  });

  return GameView;
});
