define([
    'backbone',
    'legacy/views/card'
], function (Backbone, CardView) {

    var CardsView = Backbone.View.extend({
        className: 'cards',
        initialize: function () {
            _.bindAll(this, 'onMove');
            window.addEventListener('touchmove', this.onMove);
            this.listenTo(this.collection, 'add', this.addCard);
            this.render();
        },
        addCard: function (model) {
            var card = new CardView({model: model});
            this.$el.append(card.render().$el);
        },
        render: function () {
            return this;
        },
        onMove: function (event) {
            console.log(event);
        }
    });

    return CardsView;
});