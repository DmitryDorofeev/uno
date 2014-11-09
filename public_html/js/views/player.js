define([
	'backbone',
	'tmpl/player'
], function (Backbone, tmpl) {
	var PlayerView = Backbone.View.extend({
		initialize: function () {
		},
		template: function () {
			return tmpl(this.model.toJSON());
		},
		render: function () {
			console.log(this.model.toJSON());
			this.$el.html(this.template());
			return this;
		}
	});

	return PlayerView;
});
