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
            if (data.type === 'gameInfo') {

                server.send(JSON.stringify({
                    type: 'start',
                    body: {}
                }));

//                 setTimeout(function () {
// //                debugger;
//                     server.send(JSON.stringify({
//                         type: 'cards',
//                         body: {}
//                     }));
//                 }, 3000);

            }

        });
    });

    var run = function () {

        module ('Game model test');
        gameModel.connect();
        asyncTest('gameModel.sendCards', function () {
            var cardModel = new CardModel();
            cardModel.set('cardId', 1);
            gameModel.sendCard(cardModel).done(function () {
                // debugger;
                start();
                ok(true, 'Testing of sending card is correct');
            }).fail(function () {
                start();
                ok(false, 'Testing of sending card is not correct');
            });
        });

        var listener = {};
        _.extend(listener, Backbone.Events);

        asyncTest('gameModel.sendStartMessage', function () {
            listener.listenToOnce(gameModel, 'message:start', function () {
                // debugger;
                start();
                ok (true, 'After sending start recieved load:done');
            });
        });

        // asyncTest('gameModel.sendCardsMessage', function () {
        //     listener.listenToOnce (gameModel, 'cards:render', function () {
        //         // debugger;
        //         start();
        //         ok (true, 'After sending cards message recieved cards:render');
        //     });
        // });
    };
    return {run: run};
});

