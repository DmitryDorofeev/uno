define([
    'backbone',
    'legacy/views/card'
], function (Backbone, CardView) {

    var CardsView = Backbone.View.extend({
        className: 'cards',
        initialize: function () {
            _.bindAll(this, 'onMove', 'onMotion', 'onTouch', 'onTouchDone');
            window.addEventListener('touchmove', this.onMove);
            window.addEventListener('touchstart', this.onTouch);
            window.addEventListener('touchend', this.onTouchDone);
            window.addEventListener('deviceorientation', this.onMotion);
            this.listenTo(this.collection, 'reset', this.addCards);
            this.render();
        },
        addCards: function () {
            this.collection.each(function (model) {
                var card = new CardView({model: model});
                this.$el.append(card.render().$el);
            }, this);
        },
        render: function () {
            return this;
        },
        onMove: function (event) {
            console.log(event);
            if (this.curTouch) {
                this.$el.css({left: (event.changedTouches[0].pageX - this.curTouch)});
            }
        },
        onMotion: function (event) {
            var val = event.beta,
                dop = 100;
            if (window.o && window.o === 90) {
                val = event.gamma;
                dop = 0;
            }
            this.$el.css({top: (val)*3+dop});
        },
        onTouch: function (event) {
            console.log(event);
            this.curTouch = event.changedTouches[0].pageX;
        },
        onTouchDone: function (event) {
            this.curTouch = undefined;
        }
    });

    return CardsView;
});