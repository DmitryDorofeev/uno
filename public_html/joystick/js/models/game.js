define(['backbone'], function (Backbone) {
    var startDfd;
    var GameModel = Backbone.Model.extend({
        initialize: function () {
            this.ws = new WebSocket('ws://' + location.host + '/game');
            this.ws.onopen = _.bind(this._onOpen, this);
            this.ws.onmessage = _.bind(this._onMessage, this);
        },
        render: function () {

        },
        _onOpen: function () {
            startDfd = new $.Deferred();
            this.ws.send(JSON.stringify({type: 'joystick', body: {message: 'init'}}));
        },
        _onMessage: function (msg) {
            console.log(msg)
        }
    });

    return GameModel;
});

