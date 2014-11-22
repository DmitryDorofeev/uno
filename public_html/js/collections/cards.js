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
            _.bindAll(this, 'stepDone');
        },
        addCards: function (cards) {
            console.log('adding cards', cards.cards);
            this.add(cards.cards);
        },
        sendCard: function (model) {
            this.pending = model;
            this.game.sendCard(model).done(this.stepDone);
        },
        stepDone: function () {
            console.log('step done');
            if (this.pending !== undefined) {
                this.remove(this.pending);
                console.log('remove: ', this.pending);
                //this.trigger('reset');
            }
        }
    });

    return new CardCollection();
});
