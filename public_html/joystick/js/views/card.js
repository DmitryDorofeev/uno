define([
    'backbone'
], function (Backbone) {
    var CardView = Backbone.View.extend({
        className: 'cards__card',
        initialize: function () {
            this.isFocused = false;
            _.bindAll(this, 'onMove', 'onTouch', 'onTouchDone');
            this.el.addEventListener('touchmove', this.onMove, false);
            this.el.addEventListener('touchstart', this.onTouch, false);
            this.el.addEventListener('touchend', this.onTouchDone, false);
        },
        selectCard: function (event) {
            console.log('cardId: %d', this.model.get('cardId'));
            this.model.select();
        },
        checkFocus: function (event) {
            var left = this.$el.offset().left;
            if (left >= 55 && left <= 170) {
                this.$el.addClass('cards__card_selected');
                this.isFocused = true;
            }
            else {
                this.$el.removeClass('cards__card_selected');
                this.isFocused = false;
            }
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
        onMove: function (event) {
            if (this.curTouch) {
                delta = this.curTouch - event.changedTouches[0].pageY;
                this.bottomPos = delta;
                this.$el.css({top: -delta});
            }
        },
        onTouch: function (event) {
            event.preventDefault();
            this.curTouch = event.changedTouches[0].pageY;
        },
        onTouchDone: function (event) {
            event.preventDefault();
            if (this.bottomPos > 70) {
                if (this.isFocused) {
                    this.$el.removeClass('cards__card_selected').stop().animate({width: 0, border: 'none'}, 300);
                    setTimeout(_.bind(function () {
                        this.$el.remove();
                    }, this), 300);
                    this.model.select();
                }
                else {
                    this.$el.stop().animate({top: 0}, 500);
                }
            }
            else {
                this.$el.stop().animate({top: 0}, 500);
            }
            this.curTouch = null;
        }
    });
    return CardView;
});