define(function (require) {
    var userModel = require('models/user'),
        login = require('views/login'),
        GameView = require('views/game'),
        Backbone = require('backbone'),
        emitter = _.extend({}, Backbone.Events),
        renderGame = function () {
            var game = new GameView();
            $('body').html(game.render().$el);
        },
        renderLogin = function () {
            $('body').html(login.render().$el);
        },
        onMotion = function (event) {
        var val = event.beta,
                dop = 100;
            if (window.o && window.o === 90) {
                val = event.gamma;
                dop = 0;
            }
            $('body').css({top: (val)*3+dop});
        };

    emitter.listenTo(userModel, 'logined', renderGame);
    document.addEventListener('deviceorientation', onMotion);
    userModel.isLogined().done(function () {
        userModel.trigger('logined');
    }).fail(function () {
        renderLogin();
    });

});