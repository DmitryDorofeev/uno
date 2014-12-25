define([
    'backbone',
    'collections/cards'
], function (Backbone, cardsCollection) {
    var colorDfd,
        ColorView = Backbone.View.extend({

        initialize: function () {
            this.listenTo(cardsCollection, 'color:select', this.getColor);
            _.bindAll(this, 'hide');
        },
        events: {
            'click .js-color': 'selectColor'
        },
        getColor: function (model) {
            this.show();
            colorDfd = new $.Deferred();
            colorDfd.done(function (color) {
                model.set('color', color);
                cardsCollection.sendCard(model);
            });
        },
        render: function () {
            return this;
        },
        show: function () {
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        },
        selectColor: function (event) {
            var color = $(event.target).data('color');
            colorDfd.resolve(color);
            this.hide();
        }
    });

    return new ColorView();
});
