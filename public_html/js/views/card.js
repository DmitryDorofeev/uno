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
                'background-position': '-' + (this.model.get('x')/2) + 'px -' + (this.model.get('y')/2) + 'px',
                'background-size': '1681px, 1141px'
            })
            .width(this.model.get('width')/2)
            .height(this.model.get('height')/2)
            .data('id', this.model.get('cardId'));
            return this;
        }
    });
    
    return CardView;
});