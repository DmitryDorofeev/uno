define([
    'views/base/overlay',
    'models/game',
    'tmpl/end'
], function (OverlayView, gameModel, tmpl) {
    var EndView = OverlayView.extend({

        initialize: function () {
            this.listenTo(gameModel, 'message:end', this.show);
        },

        template: function (ctx) {
            return tmpl(ctx || {});
        },

        show: function (msg) {
            this.render(msg);
            this.$('.overlay').css('display', 'block');
            this.$el.show();
        },

        /**
         * @override
         */
        close: function () {
            location.href = '/';
        }
    });

    return new EndView();
});
