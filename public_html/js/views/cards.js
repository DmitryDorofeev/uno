define([
    'backbone',
    'collections/cards'
], function (Backbone, cardsCollection) {
    
    var CardsView = Backbone.View.extend({
        collection: cardsCollection,
        initialize: function () {
            this.listenTo(this.collection, 'add', this.addCard);
        },
        addCard: function () {
            
        },
        render: function () {
            return this;
        },
        
    });
    
    return CardsView();
});