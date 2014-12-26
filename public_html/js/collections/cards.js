define([
    'backbone',
    'models/card',
    'models/game',
    'models/user'
], function (Backbone, CardModel, gameModel, userModel) {

    var stepDfd;

    var CardCollection = Backbone.Collection.extend({
        model: CardModel,
        game: gameModel,
        user: userModel,
        initialize: function () {
            this.disabled = false;
            this.listenTo(this.game, 'message:cards', this.addCards);
            this.listenTo(this.game, 'message:step', this.processStep);
            _.bindAll(this, 'stepDone', 'sendCard');
        },
        addCards: function (cards) {
            console.log('CARDS::', cards);
            this.add(cards.cards);
        },
        processCard: function (model) {
            if (model.get('color') === 'black') {
                this.trigger('color:select', model);
            }
            else {
                return this.sendCard(model);
            }
        },
        sendCard: function (model) {
            stepDfd = new $.Deferred();
            this.pending = model;
            var output = {
                type: 'card',
                body: {
                    focusOnCard: this.indexOf(model),
                    newColor: model.get('color') || null
                }
            };
            this.game.send(output);
            stepDfd.done(this.stepDone);
            return stepDfd.promise();
        },
        processStep: function (data) {
            if (data.curStepPlayerId != this.user.orderId) {
                this.trigger('cards:disable');
                this.disabled = true;
            }
            else {
                this.trigger('cards:enable');
                this.disabled = false;
            }
            if (stepDfd && stepDfd.state() === 'pending') {
                if (data.correct) {
                    stepDfd.resolve();
                }
                else {
                    stepDfd.reject();
                }
            }
        },
        stepDone: function () {
            if (this.pending) {
                this.remove(this.pending);
                console.log('remove: ', this.pending);
            }
            this.pending = null;
        },
        isDisabled: function () {
            return this.disabled;
        }
    });

    return new CardCollection();
});
