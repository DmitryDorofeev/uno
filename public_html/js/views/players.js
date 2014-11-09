define([
	'backbone',
	'collections/players'
], function (Backbone, playersCollection) {
	var PlayersView = Backbone.View.extend({
		collection: playersCollection,
		initialize: function () {
			this.listenTo(this.collection, 'reset', this.render);
		},
		render: function () {
			console.log('render players view');
		}
	});

	return new PlayersView();
});
