define([
    'backbone',
    'models/card',
    'models/game'
], function (Backbone, CardModel, gameModel) {
    var CardCollection = Backbone.Collection.extend({
        model: CardModel,
        initialize: function () {
            this.listenTo(gameModel, 'message:cards', this.addCards);
        },
        addCards: function (cards) {
            console.log('adding cards', cards.cards);
            this.add(cards.cards);
        }
    });

    return new CardCollection();
});
