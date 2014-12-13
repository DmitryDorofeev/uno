define(['backbone'], function (Backbone) {
    var GameModel = Backbone.Model.extend({
        initialize: function () {
            //this.ws = new WebSocket('ws://' + location.host + '/joystick');
        },
        render: function () {

        }
    });

    return new GameModel();
});

