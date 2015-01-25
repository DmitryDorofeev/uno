var express = require('express');
var app = express();
var fs = require('fs');
var shell = require('shelljs');
var bodyParser = require('body-parser');
var http = require('http').Server(app);
var io = require('socket.io')(http);
var crypto = require('crypto');
var shasum = crypto.createHash('sha1');

var readFile = function (input) {
    var output = '';
    var json;
    var index = input.indexOf('\n');
    while (index > -1) {
        var line = input.substring(0, index - 1);
        input = input.substring(index + 1);
        try {
            json = JSON.parse(line);
            output += line;
        }
        catch (e) {
            output += "<div class=\"redline\">" + line + "</div>";
        }
        index = input.indexOf('\n');
    }
}

app.use(bodyParser.urlencoded({
    extended: true
}));

app.post('/restart', function (req, res) {
	console.log('request');
	res.writeHead(200, { 'Content-Type': 'text/html' });
    var pass = req.body.pass;
    shasum.update(pass);
    if (shasum.digest('hex') == '90c7f35fdaf7557938bf8f0d4bf85f3ea3719286') {
	    console.log('pass ok');
        shell.cd('/var/www/uno');
        fs.unwatchFile('/var/www/uno/nohup.out');
        shell.exec('killall java');
        if (fs.exists('/var/www/uno/nohup.out')) {
	        console.log('stopping>>>');
        	fs.unlinkSync('/var/www/uno/nohup.out');
        }
        console.log('try to start');
        shell.exec('nohup java -cp uno-1.0-jar-with-dependencies.jar main.Main > nohup.out 2>&1&');
        console.log('started');
    }
    res.end('ok');
});

app.post('/remove', function (req, res) {
	res.send('ok');
});

app.use('/', express.static(__dirname + '/public'));

io.on('connection', function(socket) {
	console.log('connected');
    socket.on('watch', function () {
	    io.emit('file', readFile(fs.readFileSync('/var/www/uno/nohup.out', "utf8")));
	    fs.watchFile('/var/www/uno/nohup.out', function(curr,prev) {
	        io.emit('file', readFile(fs.readFileSync('/var/www/uno/nohup.out', "utf8")));
	    });
    });
});

http.listen(8181);
