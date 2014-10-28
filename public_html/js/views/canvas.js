define([
    'jquery',
    'underscore',
    'backbone'
], function ($, _, Backbone) {
    
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
            this.startPoint = {x: 400, y: 300};
            this.dtx = 1;
            this.amplitude = 70;
            this.f = 40;
            this.render();
            this.start();
        },
        render: function () {
            this.ctx.beginPath();
        this.ctx.arc(400, 400, 20, 0, 2 * Math.PI);
            this.ctx.fill();
        },
        show: function () {
            this.trigger('show', this);
        },
        move: function () {
            this.ctx.clearRect(0, 0, this.el.width, this.el.height);
            
            if (this.lastData !== undefined) {
                this.ctx.putImageData(this.lastData, -this.dtx, 0);
            }
            this.ctx.beginPath();
            this.ctx.moveTo(this.startPoint.x - this.dtx, (this.lastY || this.startPoint.y));
            this.lastY = this.amplitude * Math.sin(this.dx / this.f) + 300;
            this.ctx.lineTo(this.startPoint.x, this.lastY);
            this.ctx.stroke();
            this.lastData = this.ctx.getImageData(0, 0, this.startPoint.x, this.el.height);
            this.ctx.beginPath();
            this.ctx.arc(this.startPoint.x, this.amplitude * Math.sin(this.dx / this.f) + 300, 20, 0, 2 * Math.PI);
            this.ctx.fill();
            this.dx += this.dtx;
        },
        start: function () {
            this.dx = 0;
            setInterval(this.movethis, 30);
        },
        click: function () {
            console.log('click');
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