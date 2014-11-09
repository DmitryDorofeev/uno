define([
	'backbone',
	'tmpl/gamesettings'
], function (Backbone, tmpl) {
	var GameSettings = Backbone.View.extend({
	    events: {
			'click .js-send': 'sendSettings'
	    },
		initialize: function () {
			this.render();
			this.val = 2;
		},
		template: function () {
			return tmpl();
		},
		render: function () {
			this.$el.html(this.template());
		},
		show: function () {
			this.$el.show();
		},
		sendSettings: function () {
			this.val = this.$el.find('.js-select').val();
			this.trigger('game:connect', this.val);
		}
	});

	return new GameSettings();
});