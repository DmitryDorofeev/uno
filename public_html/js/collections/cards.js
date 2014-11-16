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
            this.add(cards);
        }
    });

    return new CardCollection();
});
