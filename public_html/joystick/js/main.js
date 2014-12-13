define(function (require) {
    var userModel = require('models/user'),
        login = require('views/login'),
        game = require('views/game');

    if (userModel.isLogined()) {
        game.render().show();
    }
    else {
        $('body').html(login.render().$el);
    }

});