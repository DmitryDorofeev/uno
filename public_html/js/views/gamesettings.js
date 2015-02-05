define([
	'backbone',
	'tmpl/gamesettings',
	'models/game'
], function (Backbone, tmpl, gameModel) {
	var GameSettings = Backbone.View.extend({
	    events: {
			'click .js-send': 'sendSettings',
			'click .b-select__btn': 'select'
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
            if (this.val) {
                this.trigger('game:connect', this.val);
                this.hide();
            }
		},
        select: function (event) {
            var $this = $(event.currentTarget);
            this.val = parseInt(event.currentTarget.dataset.val);
            this.$('.b-select__btn_selected').removeClass('b-select__btn_selected');
            $this.addClass('b-select__btn_selected');
        }
	});

	return new GameSettings();
});
