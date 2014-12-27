define([
    'backbone',
    'views/card'
], function (Backbone, CardView) {

    var CardsView = Backbone.View.extend({
        className: 'cards',
        leftPos: 0,
        initialize: function () {
            this.focused = 1;
            _.bindAll(this, 'onMove', 'onTouch', 'onTouchDone');
            this.el.addEventListener('touchmove', this.onMove, false);
            this.el.addEventListener('touchstart', this.onTouch, false);
            this.el.addEventListener('touchend', this.onTouchDone, false);
            this.listenTo(this.collection, 'reset', this.addCards);
            this.render();
        },
        addCards: function () {
            this.$el.html('');
            this.collection.each(function (model) {
                var card = new CardView({model: model});
                this.listenTo(card, 'focus', this.focus);
                this.$el.append(card.render().$el);
            }, this);
        },
        render: function () {
            return this;
        },
        onMove: function (event) {
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
        },
        focus: function (focusedModel) {
            this.collection.each(function (model) {
                if (focusedModel !== model) {
                    model.trigger('unfocus');
                }
            }, this);

        }
    });

    return CardsView;
});