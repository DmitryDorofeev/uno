define([
    'backbone',
    'models/game',
    'models/player'
], function (Backbone, gameModel, PlayerModel) {
	var PlayersCollection = Backbone.Collection.extend({
        model: PlayerModel,
        initialize: function () {
            this.listenTo(gameModel, 'message:start', this.start);
            this.listenTo(gameModel, 'message:step', this.step);
        },
        start: function (msg) {
            console.log(msg);
            this.add(msg.players);
        },
        step: function (msg) {
            var curStepId = msg.curStepPlayerId;

            this.each(function (model, index) {
                if (model.id == curStepId) {
                    model.trigger('activate');
                }
                else {
                    model.trigger('deactivate');
                }

                _.each(msg.cardsCount, function (player) {
                    if (model.id === player.id) {
                        model.set('cardsCount', player.count);
                    }
                });

            });
        }
	});

	return new PlayersCollection();
});
