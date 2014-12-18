define([
    'backbone',
    'views/card'
], function (Backbone, CardView) {

    var CardsView = Backbone.View.extend({
        className: 'cards',
        leftPos: 0,
        initialize: function () {
            _.bindAll(this, 'onMove', 'onTouch', 'onTouchDone');
            this.el.addEventListener('touchmove', this.onMove, false);
            this.el.addEventListener('touchstart', this.onTouch, false);
            this.el.addEventListener('touchend', this.onTouchDone, false);
            this.listenTo(this.collection, 'reset', this.addCards);
            this.render();
        },
        addCards: function () {
            this.collection.each(function (model) {
                var card = new CardView({model: model});
                card.listenTo(this, 'checkFocus', card.checkFocus);
                this.$el.append(card.render().$el);
            }, this);
            this.trigger('checkFocus');
        },
        render: function () {
            this.trigger('checkFocus');
            return this;
        },
        onMove: function (event) {
            this.trigger('checkFocus');
            if (this.curTouch) {
                var newPos;
                    delta = this.curTouch - event.changedTouches[0].pageX;
                newPos = - delta;
                this.leftPos = newPos;
                this.$el.css({left: newPos});

            }
        },
        onTouch: function (event) {
            event.preventDefault();
            this.curTouch = - this.leftPos + event.changedTouches[0].pageX;
        },
        onTouchDone: function (event) {
            this.curTouch = null;
        }
    });

    return CardsView;
});