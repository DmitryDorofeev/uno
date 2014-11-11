define([
	'backbone',
	'tmpl/player'
], function (Backbone, tmpl) {
	var PlayerView = Backbone.View.extend({
		className: 'player-wrap',
		initialize: function () {
			this.listenTo(this.model, 'change', this.render);
		},
		template: function () {
			return tmpl(this.model.toJSON());
		},
		render: function () {
			this.$el.html(this.template());
			this.$el.find('.player-cards__card').not('.player-cards__card_first').width(500/this.model.get('cardsCount'));
			return this;
		}
	});

	return PlayerView;
});
