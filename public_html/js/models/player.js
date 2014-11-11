define([
	'backbone'
], function (Backbone) {
	var PlayerModel = Backbone.Model.extend({
		initialize: function () {
			this.set('cardsCount', 7);
		}
	});

	return PlayerModel;
});
