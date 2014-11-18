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
			this.listenTo(this.collection, 'add', this.addPlayers);
		},
		render: function () {
			return this;
		},
		addPlayers: function (model) {
			if (model.get('login') === userModel.get('login')) {
				console.log('this user');
				userModel.orderId = model.get('id');
			}
			else {
				console.log('other');
				var player = new PlayerView({model: model});
				this.$el.append(player.render().$el);
			}
			this.show();
		},
		show: function () {
			this.trigger('show', this);
		}
	});

	return new PlayersView();
});
