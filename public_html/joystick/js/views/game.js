define([
    'backbone',
    'models/game',
    'legacy/tmpl/joystick/game'
], function (Backbone, GameModel, tmpl) {
    var GameView = Backbone.View.extend({
        template: function () {
            return tmpl();
        },
        render: function () {
            this.$el.html(this.template());
            this.model = new GameModel();
            return this;
        }
    });

    return new GameView();
});