define([
    'backbone',
    'collections/cards',
    'views/card'
], function (Backbone, cardsCollection, CardView) {
    
    var CardsView = Backbone.View.extend({
        className: 'cards',
        collection: cardsCollection,
        initialize: function () {
            this.listenTo(this.collection, 'add', this.addCard);
            this.render();
        },
        addCard: function (model) {
            var card = new CardView({model: model});
            this.$el.append(card.render().$el);
        },
        render: function () {
            this.$el.appendTo('body');
            return this;
        },
        
    });
    
    return new CardsView();
});