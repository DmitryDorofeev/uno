define([
    'backbone',
    'models/game'
], function (Backbone, gameModel) {
    var DeckModel = Backbone.Model.extend({
        initialize: function () {
            this.listenTo(gameModel, 'message:step', this.step);
        },
        step: function () {

        }
    });

    return new DeckModel();
});