define([
	'backbone',
	'collections/players',
	'views/player',
	'models/game',
	'models/user'
], function (Backbone, playersCollection, PlayerView, gameModel, userModel) {
	var PlayersView = Backbone.View.extend({
		collection: playersCollection,
		initialize: function () {
			this.listenTo(this.collection, 'reset', this.render);
		},
		render: function () {
			this.collection.each(function (model) {
				if (model.get('login') === userModel.get('login')) {
					userModel.id = model.get('id');
				}
				else {
					var player = new PlayerView({model: model});
					this.$el.append(player.render().$el);
				}
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
