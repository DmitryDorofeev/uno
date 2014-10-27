define([
    'routes',
    'jquery',
    'underscore',
    'backbone'
], function (router, $, _, Backbone) {
    
    var CanvasView = Backbone.View.extend({
        events: {
            'click': 'click'
        },
        tagName: 'canvas',
        className: 'hw',
        initialize: function () {
            _.bindAll(this, 'keydown');
            $(document).bind('keydown', this.keydown);
            this.el.width = 900;
            this.el.height = 600;
            this.movethis = this.move.bind(this);
            this.ctx = this.el.getContext('2d');
            this.startPoint = {x: 450, y: 300};
            this.dtx = 1;
            this.amplitude = 70;
            this.f = 40;
            this.dx = 0;
            this.render();
            this.started = false;
        },
        render: function () {
            this.ctx.beginPath();
            this.ctx.fillStyle = '#fff';
            this.ctx.fillRect(this.startPoint.x + 200, this.startPoint.y - 100, 100, 35);
            this.ctx.fillStyle = '#999';
            this.ctx.font = 'bold 12px sans-serif';
            this.ctx.fillText('Start/Pause', this.startPoint.x + 220, this.startPoint.y - 80);
        },
        show: function () {
            this.trigger('show', this);
        },
        move: function () {
            var x,
                roundPos = this.amplitude * Math.sin(this.dx / this.f) + this.startPoint.y;
            this.ctx.clearRect(0, 0, this.startPoint.x + 50, this.el.height);
            this.ctx.beginPath();
            this.ctx.moveTo(this.startPoint.x, roundPos);
            for (x = this.startPoint.x; x >= 0; x -= 1) {
                this.ctx.lineTo(x, this.amplitude * Math.sin((this.dx - (this.startPoint.x - x)) / this.f) + this.startPoint.y);
            }
            this.ctx.stroke();
            this.ctx.beginPath();
            this.ctx.arc(this.startPoint.x, roundPos, 20, 0, 2 * Math.PI);
            this.ctx.fill();
            this.dx += this.dtx;
        },
        start: function () {
            this.interval = setInterval(this.movethis, 30);
            this.started = true;
        },
        pause: function () {
            clearInterval(this.interval);
            this.started = false;
        },
        click: function (event) {
            var coordX = event.pageX - $('#page').offset().left,
                coordY = event.pageY - $('#page').offset().top;
            if ((coordX >= this.startPoint.x + 200) && (coordX <= this.startPoint.x + 300) && (coordY >= this.startPoint.y - 90) && (coordY <= this.startPoint.y - 55)) {
                if (this.started) {
                    this.pause();
                } else {
                    this.start();
                }
            }
        },
        keydown: function (event) {
            switch (event.keyCode) {
            case 38:
                this.amplitude += 1;
                break;
            case 40:
                this.amplitude -= 1;
                break;
            case 39:
                this.f -= 1;
                break;
            case 37:
                this.f += 1;
                break;
            }
        }
    });
    
    return new CanvasView();
});