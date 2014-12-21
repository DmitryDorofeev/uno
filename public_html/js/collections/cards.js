define([
    'backbone',
    'models/card',
    'models/game'
], function (Backbone, CardModel, gameModel) {

    var stepDfd;

    var CardCollection = Backbone.Collection.extend({
        model: CardModel,
        game: gameModel,
        initialize: function () {
            this.listenTo(this.game, 'message:cards', this.addCards);
            this.listenTo(this.game, 'message:step', this.processStep);
            _.bindAll(this, 'stepDone');
        },
        addCards: function (cards) {
            console.log('adding cards', cards.cards);
            this.add(cards.cards);
        },
        sendCard: function (model) {
            stepDfd = new $.Deferred();
            this.pending = model;
            var output = {
                type: 'card',
                body: {
                    focusOnCard: model.collection.indexOf(model),
                    newColor: model.get('color') || null
                }
            };
            this.game.send(output);
            stepDfd.done(this.stepDone);
            return stepDfd.promise();
        },
        processStep: function (data) {
            if (stepDfd && stepDfd.state() === 'pending') {
                if (data.correct) {
                    stepDfd.resolve();
                }
            }
        },
        stepDone: function () {
            if (this.pending !== undefined) {
                this.remove(this.pending);
                console.log('remove: ', this.pending);
            }
        }
    });

    return new CardCollection();
});
