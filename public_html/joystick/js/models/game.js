define(['backbone'], function (Backbone) {

    var startDfd, cardsDfd;
    var GameModel = Backbone.Model.extend({
        initialize: function () {
            startDfd = new $.Deferred();
            this.ws = new WebSocket('ws://' + location.host + '/game');
            this.ws.onopen = _.bind(this._onOpen, this);
            this.ws.onmessage = _.bind(this._onMessage, this);
            document.addEventListener('touchstart', function (event) {
                event.preventDefault();
            });
        },
        _onOpen: function () {
            this.ws.send(JSON.stringify({type: 'joystick', body: {message: 'init'}}));
        },
        _onMessage: function (msg) {
            var data = JSON.parse(msg.data);
            if (data.type === 'cards' && data.body.correct){
                this.trigger('message:cards', data.body);
                startDfd.resolve();
            }
            else {
                startDfd.reject();
            }
            if (cardsDfd && (cardsDfd.state() === 'pending') && (data.type === 'step') && data.body.correct){
                alert('lal');
            }
        },
        gameStart: function () {
            return startDfd.promise();
        },
        sendCard: function (index) {
            cardsDfd = new $.Deferred();
            this.ws.send(JSON.stringify({type: 'joystick', body: {message: 'throwCard', focusOnCard: index}}));
            return cardsDfd.promise();
        }
    });

    return GameModel;
});

