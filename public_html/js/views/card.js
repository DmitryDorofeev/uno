define([
    'backbone'
], function (Backbone) {
    
    var CardView = Backbone.View.extend({
        className: 'cards__card',
        initialize: function () {
            this.listenTo(this.model, 'remove', this.remove);
        },
        onMove: function (event) {
            if (this.curTouch) {
                var newPos;
                delta = this.curTouch - event.changedTouches[0].pageY;
                newPos = - delta;
                this.bottomPos = newPos;
                this.$el.css({left: newPos});

            }
        },
        onTouch: function (event) {
            event.preventDefault();
            this.curTouch = - this.bottomPos + event.changedTouches[0].pageY;
        },
        onTouchDone: function (event) {
            this.curTouch = null;
        },
        events: {
            'click': 'selectCard'
        },
        selectCard: function (event) {
            console.log('cardId: %d', this.model.get('cardId'));
            this.model.select();
        },
        render: function () {
            this.$el.css({
                'background-position': '-' + this.model.get('x') + 'px -' + this.model.get('y') + 'px',
                'background-size': '1681px, 1141px'
            })
            .width(this.model.get('width'))
            .height(this.model.get('height'))
            .data('id', this.model.get('cardId'));
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