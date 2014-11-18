define([
    'backbone',
    'models/game',
    'models/player'
], function (Backbone, gameModel, PlayerModel) {
	var PlayersCollection = Backbone.Collection.extend({
        model: PlayerModel,
        initialize: function () {
            this.listenTo(gameModel, 'message:start', this.start);
        },
        start: function (msg) {
            console.log(msg);
            this.add(msg.players);
        }
	});

	return new PlayersCollection();
});
