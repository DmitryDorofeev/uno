define([
	'backbone',
	'tmpl/player'
], function (Backbone, tmpl) {
	var PlayerView = Backbone.View.extend({
		className: 'player-wrap',
		initialize: function () {
			this.listenTo(this.model, 'change', this.render);
			this.listenTo(this.model, 'activate', this.activate);
			this.listenTo(this.model, 'deactivate', this.deactivate);
		},
		template: function () {
			return tmpl(this.model.toJSON());
		},
		render: function () {
			console.log('render');
			this.$el.html(this.template());
			this.$el.find('.player-cards__card').not('.player-cards__card_first').width(500/this.model.get('cardsCount'));
			return this;
		},
		activate: function () {
			this.$el.addClass('player-wrap_active');
		},
		deactivate: function () {
			this.$el.removeClass('player-wrap_active');
		}
	});

	return PlayerView;
});
