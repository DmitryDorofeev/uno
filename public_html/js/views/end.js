define([
    'views/base/layer',
    'models/game',
    'tmpl/all'
], function (Layer, gameModel, template) {

    var EndView = Layer.extend({

        initialize: function () {
            this.listenTo(gameModel, 'message:end', this.show);
        },

        template: function (ctx) {
            return template({
                block: 'end',
                scores: ctx || {}
            });
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
