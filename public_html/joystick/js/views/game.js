define([
    'backbone',
    'models/game',
    'legacy/tmpl/joystick/game'
], function (Backbone, GameModel, tmpl) {
    var GameView = Backbone.View.extend({
        initialize: function () {
            if (window.DeviceMotionEvent) {
                window.addEventListener('devicemotion', this.motion);
            }
            else {
                console.log('no motion avaliable');
                this.trigger('nomotion');
            }
        },
        template: function () {
            return tmpl();
        },
        render: function () {
            this.$el.html(this.template());
            this.model = new GameModel();
            return this;
        },
        motion: function (event) {
            console.log(event);
        }
    });

    return new GameView();
});