define([
	'jquery',
	'backbone',
    'models/user'
], function ($, Backbone, userModel) {

	var stepDfd;

	var GameModel = Backbone.Model.extend({
		initialize: function () {
			this.connection = undefined;
			this.status = 0;
            this.players = 2;
			_.bindAll(this, 'onConnect', 'onMessage');
		},
		connect: function () {
            this.trigger('load:start', 'Подключение...');
            if (this.connection !== undefined) {
                this.connection.close();
            }
            this.connection = new WebSocket('ws://' + location.host + '/game');
			this.connection.onopen = this.onConnect;
			this.connection.onmessage = this.onMessage;
		},
		close: function () {
			if (this.connection !== undefined) {
				this.connection.close();
			}
		},
		onConnect: function () {
			this.status = 1;
            var players = this.players;
            var sendObj = {
                type: 'gameInfo',
                body: {
                    players: players
                }
            };
			this.connection.send(JSON.stringify(sendObj));
            this.trigger('load:done');
            this.trigger('load:start', 'Ожидание игроков...');
		},
		onMessage: function (msg) {
            var data = JSON.parse(msg.data);
            console.log('from server: ', data);
            if (data.type === 'start') {
                this.trigger('load:done');
            }
			if (data.type === 'cards') {
				this.trigger('cards:render');
			}
			if (data.type === 'step') {
				if (stepDfd && stepDfd.state() === 'pending') {
					if (data.body.correct) {
						stepDfd.resolve();
					}
				}
			}
			this.trigger('message:' + data.type, data.body);
		},
		sendCard: function (model) {
			stepDfd = new $.Deferred();
			var output = {
				type: 'card',
				body: {
					cardId: model.get('cardId')
				}
			};
			this.connection.send(JSON.stringify(output));
			return stepDfd.promise();
		},
		orient: function (event) {
			console.log(event.alpha);
		},
		getCard: function () {
			this.connection.send(JSON.stringify({type: "card", body: {focusOnCard: -1}}));
		}
	});

	return new GameModel();
});
