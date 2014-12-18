define([
    'backbone'
], function (Backbone) {
    var CardView = Backbone.View.extend({
        className: 'cards__card',
        initialize: function () {
        },
        selectCard: function (event) {
            console.log('cardId: %d', this.model.get('cardId'));
            this.model.select();
        },
        checkFocus: function (event) {
            var left = this.$el.offset().left;
            if (left >= 55 && left <= 170) {
                this.$el.addClass('cards__card_selected');
            }
            else {
                this.$el.removeClass('cards__card_selected');
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
        }
    });
    return CardView;
});