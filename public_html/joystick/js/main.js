define(function (require) {
    var userModel = require('models/user'),
        login = require('views/login'),
        game = require('views/game'),
        Backbone = require('backbone'),
        emitter = _.extend({}, Backbone.Events);

    emitter.listenTo(userModel, 'logined', gameRender);
    function gameRender() {
        $('body').html(game.render().$el);
    }
    if (userModel.isLogined()) {
        gameRender();
    }
    else {
        $('body').html(login.render().$el);
    }

});