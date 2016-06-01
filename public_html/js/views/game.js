define(function (require) {

    var $ = require('jquery');
    var Backbone = require('backbone');
    var createjs = require('createjs');
    var Canvas = require('views/canvas');
    var template = require('tmpl/all');
    var userModel = require('models/user');
    var gameModel = require('models/game');
    var settingsView = require('views/gamesettings');
    var playersView = require('views/players');
    var cardsView = require('views/cards');
    var deckView = require('views/deck');
    var colorView = require('views/color');
    var endView = require('views/end');

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

            // $(window).on('resize', this.resetCanvas.bind(this))
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

            return this;
        },

        show: function () {
            // if (userModel.isLogined()) {
                this.trigger('show', this);
                this.$el.show();
                var canvas = new Canvas(this.$('canvas')[0]);
                canvas.addCard({pageX: 0, pageY: 0, x: 120, y: 180, width: 120, height: 180});
                canvas.addCard({pageX: 120, pageY: 0, x: 240, y: 180, width: 120, height: 180});
                canvas.addCard({pageX: 240, pageY: 0, x: 360, y: 180, width: 120, height: 180});

            // }
            // else {
            //   userModel.trigger('login:no');
            // }
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
