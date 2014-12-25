define([
    'backbone'
], function (Backbone) {
    
    var CardView = Backbone.View.extend({
        className: 'cards__card',
        initialize: function () {
            this.listenTo(this.model, 'remove', this.remove);
        },
        events: {
            'click': 'selectCard'
        },
        selectCard: function (event) {
            if (!this.model.collection.isDisabled()) {
                this.model.select();
            }
        },
        render: function () {
            this.$el.css({
                'background-position': '-' + this.model.get('x') + 'px -' + this.model.get('y') + 'px',
                'background-size': '1681px, 1141px'
            })
            .width(this.model.get('width'))
            .height(this.model.get('height'));
            return this;
        },
        remove: function () {
            this.$el
                .animate({'bottom': 100, opacity: 0}, 500)
                .delay(500)
                .animate({width: 0}, 200)
                .delay(200);
        }
    });
    
    return CardView;
});