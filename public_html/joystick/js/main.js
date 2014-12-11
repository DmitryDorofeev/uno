define(function (require) {
    var login = require('views/login');
    var ws = new WebSocket('ws://' + location.host + '/joystick');
});