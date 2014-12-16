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
            if (data.type === 'gameinfo') {
                server.send(JSON.stringify({
                    type: 'start',
                    body: {}
                }));
            }
        });
    });

    var run = function () {

        var listener = {};
        _.extend(listener, Backbone.Events);

        module ('Sending start test');

        asyncTest('gameModel.sendStartMessage', function () {
        gameModel.connect();
            listener.listenToOnce(gameModel, 'load:done', function () {
                start();
                ok (1===1, 'After sending start recieved load:done');
            });
        });
    };

    return {run: run};
});