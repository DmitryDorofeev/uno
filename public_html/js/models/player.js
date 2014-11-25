define([
	'backbone',
	'models/game'
], function (Backbone, gameModel) {
	var PlayerModel = Backbone.Model.extend({
		initialize: function () {
			this.set('cardsCount', 7);
		}
	});

	return PlayerModel;
});
