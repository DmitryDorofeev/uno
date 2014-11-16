define([
    'backbone'
], function (Backbone) {
    
    var CardView = Backbone.View.extend({
        className: 'card',
        initialize: function () {
            
        },
        events: {
            'click': 'selectCard'
        },
        selectCard: function (event) {
            this.model.select();
        },
        render: function () {
            this.$el.css({
                'background-position': this.model.get('x') + ',' + this.model.get('y')     
            })
            .width(100)
            .height(200);
            return this;
        }
    });
    
    return CardView;
});