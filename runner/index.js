var express = require('express');
var app = express();
var fs = require('fs');
var shell = require('shelljs');
var bodyParser = require('body-parser');
var http = require('http').Server(app);
var io = require('socket.io')(http);

app.use(bodyParser.urlencoded({
    extended: true
}));

app.use('/', express.static(__dirname + '/public'));

app.get('/', function (req, res) {
    res.render('index')
});

app.post('/restart', function (req, res) {
    var pass = req.body.pass;
    if (pass == 'fuckMe!!') {
        shell.cd('/var/www/uno');
        shell.exec('killall java');
        fs.unlinkSync('/var/www/uno/nohup.out');
        shell.exec('nohup java -cp target/uno-1.0-jar-with-dependencies.jar main.Main &');
    }
});

app.post('/remove', function () {

});

io.on('connection', function(socket){
    fs.watchFile('/var/www/uno/nohup.out', function(curr,prev) {
        console.log('azaza');
    });
});

http.listen(8181)
