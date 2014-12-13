define([
    'backbone',
    'models/game'
], function (Backbone, gameModel) {
    var GameView = Backbone.View.extend({
        model: gameModel
    });

    return new GameView();
});