define([
    'backbone',
    'models/card',
    'models/game'
], function (Backbone, CardModel, gameModel) {
    var CardCollection = Backbone.Collection.extend({
        model: CardModel,
        game: gameModel,
        initialize: function () {
            this.listenTo(gameModel, 'message:cards', this.addCards);
        },
        addCards: function (cards) {
            console.log('adding cards', cards.cards);
            this.add(cards.cards);
        },
        sendCard: function (model) {
            this.game.sendCard(model);
        }
    });

    return new CardCollection();
});
