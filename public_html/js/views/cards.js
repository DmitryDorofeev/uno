define([
    'backbone',
    'collections/cards',
    'views/card',
    'tmpl/cards'
], function (Backbone, cardsCollection, CardView, tmpl) {
    
    var CardsView = Backbone.View.extend({
        collection: cardsCollection,
        initialize: function () {
            this.render();
            this.listenTo(this.collection, 'add', this.addCard);
        },
        template: function () {
            return tmpl();
        },
        addCard: function (model) {
            var card = new CardView({model: model});
            this.$wrap.append(card.render().$el);
        },
        render: function () {
            this.$el.html(this.template());
            this.$wrap = this.$('.js-cards');
            return this;
        }
    });
    
    return new CardsView();
});