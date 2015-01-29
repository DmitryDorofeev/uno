var express = require('express');
var app = express();
var fs = require('fs');
var shell = require('shelljs');
var bodyParser = require('body-parser');
var http = require('http').Server(app);
var io = require('socket.io')(http);
var crypto = require('crypto');

var readFile = function (input) {
    var output = '';
    var json;
    var index = input.indexOf('\n');
    while (index > -1) {
        var line = input.substring(0, index);
        input = input.substring(index + 1);
        try {
	        console.log(line);
            json = JSON.parse(line);
            output += "<div class=\"line\">" + (json.type || json.title) + "</div>\n";
            output += "<div class=\"greenline\">" + JSON.stringify((json.message || json.body)) + "</div>\n";
        }
        catch (e) {
            output += "<div class=\"redline\">" + line + "</div>\n";
        }
        index = input.indexOf('\n');
    }
    return output;
}

app.use(bodyParser.urlencoded({
    extended: true
}));

app.post('/restart', function (req, res) {
	console.log('request');
	res.writeHead(200, { 'Content-Type': 'text/html' });
    var pass = req.body.pass;
    var shasum = crypto.createHash('sha1');
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

app.post('/deploy', function (req, res) {
	res.writeHead(200, { 'Content-Type': 'text/html' });
    var pass = req.body.pass;
    console.log('PASS', pass);
    var shasum = crypto.createHash('sha1');
    shasum.update(pass);
    if (shasum.digest('hex') == '90c7f35fdaf7557938bf8f0d4bf85f3ea3719286') {
	    console.log('pass ok');
        shell.cd('/var/www/uno');
        var info = shell.exec('git pull origin master');
        console.log('GIT OUT ', info);
        res.end(info.output);
    }
    else {
	    res.end('ok');
    }
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
    
    socket.on('log', function (msg) {
	    fs.appendFile('/var/www/uno/nohup.out', msg, function (err) {
		    
	    });
    });
});

http.listen(8181);
