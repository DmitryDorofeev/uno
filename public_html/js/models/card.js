define([
    'backbone',
    'models/game'
], function (Backbone, gameModel) {
    var CardModel = Backbone.Model.extend({
        initialize: function () {
            this.listenTo(gameModel, 'message:card', this.onMessage);
        },
        onMessage: function () {
            
        }
    });

    return CardModel;
});
