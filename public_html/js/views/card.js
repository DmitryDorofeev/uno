define([
    'backbone'
], function (Backbone) {
    
    var CardView = Backbone.View.extend({
        initialize: function () {
            
        },
        events: {
            'click': 'selectCard'
        },
        selectCard: function (event) {
            this.model.select();
        }
    });
    
    return CardView();
});