define([
    'backbone',
    'models/game'
], function (Backbone, gameModel) {
    var DeckModel = Backbone.Model.extend({
        initialize: function () {
            this.listenTo(gameModel, 'message:step', this.step);
        },
        step: function (data) {
            if (data.correct) {
                this.set(data.cards[0]);
            }
        },
        getCard: function () {
            gameModel.send({type: 'card', body: {focusOnCard: -1}});
        },
        sayUno: function () {
            gameModel.send({type: 'uno', body: {}})
        }
    });

    return new DeckModel();
});