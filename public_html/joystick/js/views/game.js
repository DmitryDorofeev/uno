define([
    'backbone',
    'models/game',
    'legacy/tmpl/joystick/game',
    'views/cards',
    'views/deck',
    'collections/cards'
], function (Backbone, GameModel, tmpl, CardsView, DeckView, CardsCollection) {
    var GameView = Backbone.View.extend({
        className: 'page',
        initialize: function () {
            this.model = new GameModel(),
            this.cards = new CardsView({collection: new CardsCollection([], {game: this.model})}),
            this.deck = new DeckView({model: this.model});
            this.model.gameStart().done(_.bind(function () {
                this.$el.html(this.cards.render().$el);
                this.$el.append(this.deck.render().$el);
            }, this));
        },
        template: function () {
            return tmpl();
        },
        render: function () {
            this.$el.html(this.template());
            return this;
        }
    });
    return GameView;
});