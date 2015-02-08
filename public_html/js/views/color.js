define([
    'views/base/overlay',
    'collections/cards',
    'tmpl/color'
], function (OverlayView, cardsCollection, tmpl) {
    var colorDfd,

        /**
         * @constructor
         * @implements {Backbone}
         */
        ColorView = OverlayView.extend({

            /**
             * @override
             */
            initialize: function () {
                this.listenTo(cardsCollection, 'color:select', this.getColor);
                _.bindAll(this, 'close');
            },

            /**
             * @override
             */
            template: function () {
                return tmpl();
            },

            /**
             * @override
             */
            customEvents: {
                'click .js-color': 'selectColor',
                'click .js-close': 'close'
            },

            getColor: function (model) {
                this.show();
                colorDfd = new $.Deferred();
                colorDfd.done(function (color) {
                    model.set('color', color);
                    cardsCollection.sendCard(model).fail(function () {
                        model.set('color', 'black');
                    });
                });
            },

            show: function () {
                this.$('.overlay').css('display', 'block');
                this.$el.show();
                this.$('.color').show();
            },

            /**
             * @override
             */
            close: function () {
                this.$el.css('display', 'none');
                colorDfd.reject();
            },

            selectColor: function (event) {
                var color = $(event.target).data('color');
                colorDfd.resolve(color);
                this.close();
            }
        });

    return new ColorView();
});
