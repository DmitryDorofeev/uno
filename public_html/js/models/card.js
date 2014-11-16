define([
    'backbone',
    'models/game'
], function (Backbone, gameModel) {
    var CardModel = Backbone.Model.extend({
        initialize: function () {
        }
    });

    return CardModel;
});
