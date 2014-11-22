define([
    'jquery',
    'backbone',
    'models/deck',
    'tmpl/deck'
], function ($, Backbone, deckModel, tmpl) {
    var DeckView = Backbone.View.extend({
        model: deckModel,
        initialize: function () {
            this.listenTo(this.model, 'change', this.changeCard);
            this.$el.hide();
        },
        template: function () {
            return tmpl();
        },
        render: function () {
            this.$el.html(this.template());
            return this;
        },
        changeCard: function () {
            this.$el.find('.current').css({
                'background-position': '-' + this.model.get('x') + 'px ' + this.model.get('y') + 'px'
            });
        },
        show: function () {
            this.$el.show();
        }
    });

    return new DeckView();
});