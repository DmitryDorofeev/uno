define([
    'jquery',
    'backbone',
    'models/deck',
    'tmpl/deck'
], function ($, Backbone, deckModel, tmpl) {

    var colors = {
        green: 'rgb(0,178, 87)',
        red: 'rgb(255, 49, 64)',
        blue: 'rgb(107, 36, 255)',
        yellow: 'rgb(255, 168, 0)'
    };

    var DeckView = Backbone.View.extend({
        model: deckModel,
        events: {
            'click .js-deck': 'getCards',
            'click .js-uno': 'sayUno'
        },
        initialize: function () {
            this.isFirst = true;
            this.listenTo(this.model, 'add change', this.changeCard);
            this.$el.hide();
        },
        template: function () {
            return tmpl();
        },
        render: function () {
            this.$el.html(this.template());
            return this;
        },
        changeCard: function () {
            var time = this.isFirst ? 0 : 300;
            this.isFirst = false;
            this.$('.js-table').css({backgroundColor: colors[this.model.get('color')]});
            this.$('.b-table__new').css({
                'background-position': '-' + this.model.get('x') + 'px -' + this.model.get('y') + 'px'
            });

            this.$('.b-table__new').animate({top: 0, opacity: 1}, time, _.bind(function () {
                this.$('.b-table__current').css({
                    'background-position': '-' + this.model.get('x') + 'px -' + this.model.get('y') + 'px'
                });

                this.$('.b-table__new').css({top: -30, opacity: 0});
            }, this));
        },
        show: function () {
            this.$el.show();
        },
        getCards: function () {
            this.model.getCard();
        },
        sayUno: function () {
            Backbone.Events.trigger('error', 'UNO!');
            this.model.sayUno();
        }
    });

    return new DeckView();
});