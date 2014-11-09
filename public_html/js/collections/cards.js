define([
    'backbone',
    'models/game'
], function (Backbone, gameModel) {
    var CardCollection = Backbone.Collection.extend({
        initialize: function () {
            this.listenTo(gameModel, 'message:cards');
        }
    });

    return new CardCollection();
});
