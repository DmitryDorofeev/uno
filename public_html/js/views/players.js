define([
	'backbone',
	'collections/players',
	'views/player',
	'models/game'
], function (Backbone, playersCollection, PlayerView, gameModel) {
	var PlayersView = Backbone.View.extend({
		collection: playersCollection,
		initialize: function () {
			this.listenTo(this.collection, 'reset', this.render);
		},
		render: function () {
			this.collection.each(function (model) {
				var player = new PlayerView({model: model});
				this.$el.append(player.render().$el);
			}, this);
			this.show();
			return this;
		},
		show: function () {
			this.trigger('show', this);
		}
	});

	return new PlayersView();
});
