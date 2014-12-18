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
        };

    emitter.listenTo(userModel, 'logined', renderGame);

    userModel.isLogined().done(function () {
        userModel.trigger('logined');
    }).fail(function () {
        renderLogin();
    });

});