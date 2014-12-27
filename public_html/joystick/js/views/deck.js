define([
    'backbone',
], function (Backbone) {
    var DeckView = Backbone.View.extend({
        tagName: 'button',
        className: 'btn deck',
        initialize: function () {
        },
        getCard: function (event) {
            event.preventDefault();
            this.model.ws.send(JSON.stringify({type: 'joystick', body: {message: 'getCard'}}));
        },
        render: function () {
            this.$el.text('GET');
            this.el.addEventListener('touchstart', _.bind(this.getCard, this));
            return this;
        }
    });

    return DeckView;
});