define([
    'jquery',
    'backbone',
    'tmpl/all',
    'models/user',
    'models/game',
    'views/gamesettings',
    'views/players',
    'views/cards',
    'views/deck',
    'views/color',
    'views/end'
], function (
    $,
    Backbone,
    template,
    userModel,
    gameModel,
    settingsView,
    playersView,
    cardsView,
    deckView,
    colorView,
    endView
) {
    var first = true;
  var GameView = Backbone.View.extend({
    model: userModel,
    game: gameModel,
    settings: settingsView,
    color: colorView,
    initialize: function() {
        this.views = {};
        this.register('settings', this.settings);
        this.register('color', colorView);
        this.register('players', playersView);
        this.register('cards', cardsView);
        this.register('deck', deckView);
        this.register('end', endView);
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
      return template({ block: 'game' });
    },
    render: function() {
        this.$el.html(this.template());
        this.$game = this.$('.game');

        _.forEach(this.views, function(view) {
            this.$game.append(view.render().$el);
            view.$el.hide();
        }, this);

        return this;
    },
    show: function () {
      if (userModel.isLogined()) {
          this.trigger('show', this);
          this.$el.show();
          if (this.game.connection === undefined) {
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
      this.settings.show();
    },
    sendSettings: function (val) {
      this.game.players = val;
      this.game.connect();
    },
    renderCards: function () {
        this.views.cards.render().show();
        this.views.deck.show();
        first = false;
    },
      showError: function (msg) {
          this.trigger('error', msg);
      }
  });

  return GameView;
});
