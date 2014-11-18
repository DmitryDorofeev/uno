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
			console.log('render', this.collection);
			this.collection.each(function (model) {
				if (model.get('login') === userModel.get('login')) {
					console.log('this user');
					userModel.id = model.get('id');
				}
				else {
					console.log('other');
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
