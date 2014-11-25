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
            console.log('STEP FUCK', msg);
            var cardsCount;
            if ((this.curStep === undefined) || (this.curStep == msg.curStepPlayerId)) {
                this.curStep = msg.curStepPlayerId;
            }
            else {
                cardsCount = this.at(this.curStep).get('cardsCount');
                this.at(this.curStep).set('cardsCount', cardsCount - 1);
                this.curStep = msg.curStepPlayerId;
            }
        }
	});

	return new PlayersCollection();
});
