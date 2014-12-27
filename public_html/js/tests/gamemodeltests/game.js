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

