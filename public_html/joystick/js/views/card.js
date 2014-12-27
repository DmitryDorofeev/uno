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
            this.listenTo(this.model, 'unfocus', this.unfocus);
        },
        focus: function () {
            this.trigger('focus', this);
            this.$el.addClass('cards__card_selected');
            this.isFocused = true;
        },
        unfocus: function () {
            this.$el.removeClass('cards__card_selected');
            this.isFocused = false;
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
            this.focus();
            this.curTouch = event.changedTouches[0].pageY;
        },
        onTouchDone: function (event) {
            event.preventDefault();
            if (this.bottomPos > 70) {
                if (this.isFocused) {
                    this.$el.removeClass('cards__card_selected').stop();
                    this.model.collection.select(this.model).done(_.bind(function () {
                        this.$el.animate({width: 0, border: 'none'}, 300);
                        setTimeout(_.bind(function () {
                            this.$el.remove();
                        }, this), 300);
                    }, this)).fail(
                        _.bind(function () {
                            this.$el.stop().animate({top: 0}, 300);
                        }, this)
                    );
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