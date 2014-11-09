define([
	'backbone'
], function (Backbone) {
	var GameModel = Backbone.Model.extend({
		initialize: function () {
			this.connection = undefined;
			this.status = 0;
			_.bindAll(this, 'onConnect', 'onMessage');
		},
		connect: function () {
			this.connection = new WebSocket('ws://127.0.0.1:8080/gameplay');
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
			this.connection.send({type: 'gameInfo', players: this.playersNumber});
		},
		onMessage: function () {
			console.log('fuck');
		}
	});

	return new GameModel();
});