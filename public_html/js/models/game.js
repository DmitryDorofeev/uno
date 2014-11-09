define([
	'backbone',
    'models/user'
], function (Backbone, userModel) {
	var GameModel = Backbone.Model.extend({
		initialize: function () {
			this.connection = undefined;
			this.status = 0;
            this.players = 2;
			_.bindAll(this, 'onConnect', 'onMessage');
		},
		connect: function () {
            this.trigger('load:start', 'Подключение...');
			this.connection = new WebSocket('ws://127.0.0.1:8080/game');
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
                    login: userModel.get('login'),
                    players: players
                }
            };
            console.log(JSON.stringify(sendObj));
			this.connection.send(JSON.stringify(sendObj));
            this.trigger('load:done');
            this.trigger('load:start', 'Ожидание игроков...');
		},
		onMessage: function (msg) {
            console.log(msg);
            if (msg.type === 'start') {
                this.trigger('load:done');
            }
			this.trigger('message:' + msg.type, msg.body);
		}
	});

	return new GameModel();
});
