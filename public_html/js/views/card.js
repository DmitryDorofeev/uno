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
                'background-position': '' + this.model.get('x') + 'px ' + this.model.get('y') + 'px',
                'background-size': '1681px, 1141px'
            })
            .width(this.model.get('width')/2)
            .height(this.model.get('height')/2);
            return this;
        }
    });
    
    return CardView;
});