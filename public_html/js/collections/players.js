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
            if (!this.curStep || (this.curStep == msg.curStepPlayerId)) {
                this.curStep = msg.curStepPlayerId;
                return;
            }
            var model = this.at(this.curStep);
            model.set('cardsCount', model.get('cardsCount') - 1);
            this.curStep = msg.curStepPlayerId;
        }
	});

	return new PlayersCollection();
});
