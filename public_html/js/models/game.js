define([
	'jquery',
	'backbone',
    'models/user'
], function ($, Backbone, userModel) {

	var GameModel = Backbone.Model.extend({
		initialize: function () {
			this.connection = undefined;
			this.status = 0;
            this.players = 2;
			_.bindAll(this, 'onConnect', 'onMessage');
		},
		connect: function () {
            this.trigger('load:start', 'Подключение...');
            if (this.connection === undefined) {
				this.connection = new WebSocket('ws://' + location.host + '/game');
            }
			this.connection.onopen = this.onConnect;
			this.connection.onmessage = this.onMessage;
		},
		send: function (data) {
			console.log({type: 'TO_SERVER:', message: data});
			this.connection.send(JSON.stringify(data));
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
			this.send(sendObj);
            this.trigger('load:done');
            this.trigger('load:start', 'Ожидание игроков...');
		},
		onMessage: function (msg) {
            var data = JSON.parse(msg.data);
            console.log({type: 'FROM_SERVER:', message: data});
            if (data.type === 'start') {
                this.trigger('load:done');
				this.trigger('cards:render');
            }
			if (data.type === 'end') {
				this.connection.close();
			}
			this.trigger('message:' + data.type, data.body);
		}
	});

	return new GameModel();
});
