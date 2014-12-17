define([
    'backbone',
    'jquery',
    'models/game',
    'models/card',
    'mock-socket'
], function (Backbone, $, gameModel, CardModel) {
    window.WebSocket = MockSocket;

    var exampleServer = new WebSocketServer('ws://' + location.host + '/game');
    exampleServer.on('connection', function(server) {
        server.on('message', function(msg) {
            var data = JSON.parse(msg.data);
            if (data.type === 'gameInfo') {
                server.send(JSON.stringify({
                    type: 'cards',
                    body: {}
                }));
            }
        });
    });

    var run = function () {

        var listener = {};
        _.extend(listener, Backbone.Events);

        module ('Sending cards message test');

        asyncTest('gameModel.sendCardsMessage', function () {
            gameModel.connect();
            listener.listenToOnce (gameModel, 'cards:render', function () {
                start();
                ok (1===1, 'After sending cards message recieved cards:render');
            });
        });
    };

    return {run: run};
});