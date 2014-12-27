define([
    'backbone',
    'legacy/models/card'
], function (Backbone, CardModel) {
    var CardCollection = Backbone.Collection.extend({
        model: CardModel,
        initialize: function (models, options) {
            this.game = options.game;
            this.listenTo(options.game, 'message:cards', this.addCards);
            _.bindAll(this, 'stepDone');
        },
        addCards: function (cards) {
            console.log('adding cards', cards.cards);
            this.reset(cards.cards);
        },
        sendCard: function (model) {

        },
        stepDone: function () {
            if (this.pending !== undefined) {
                this.remove(this.pending);
                console.log('remove: ', this.pending);
            }
        },
        select: function (model) {
            this.pending = model;
            return this.game.sendCard(this.indexOf(model));
        }
    });

    return CardCollection;
});