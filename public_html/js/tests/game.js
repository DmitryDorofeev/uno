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
        server.on('message', function(data) {
            if (data.type === 'card') {
                setTimeout(function () {
                    server.send(JSON.stringify({
                        type: 'step',
                        body: {
                            correct: true
                        }
                    }));
                }, 1000);
            }
        });
    });

    var run = function () {

        module ('Game model test');
        
        asyncTest('gameModel.', function () {
            gameModel.connect();
            var cardModel = new CardModel();
            cardModel.set('cardId', 1);
            gameModel.sendCard(cardModel).done(function () {
                start();
                ok(true, 'Testing of sending card is correct');
            }).fail(function () {
                start();
                ok(false, 'Testing of sending card is not correct');
            });
        });
    }
    return {run: run};
    }
);

