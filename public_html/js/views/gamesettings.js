define([
	'backbone',
	'tmpl/gamesettings',
	'models/game'
], function (Backbone, tmpl, gameModel) {
	var GameSettings = Backbone.View.extend({
	    events: {
			'click .js-send': 'sendSettings'
	    },
		initialize: function () {
			this.listenTo(gameModel, 'message:start', this.hide);
			this.render();
			this.val = 2;
		},
		template: function () {
			return tmpl();
		},
		render: function () {
			this.$el.html(this.template());
			return this;
		},
		show: function () {
			this.$el.show();
		},
		hide: function () {
			this.$el.hide();
		},
		sendSettings: function () {
			this.val = +this.$el.find('.js-select').val();
			this.trigger('game:connect', this.val);
		}
	});

	return new GameSettings();
});
