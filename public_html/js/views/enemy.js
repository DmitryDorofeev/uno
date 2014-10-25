define([
    'backbone',
    'models/enemy'
], function (Backbone, enemyModel) {
    
    var EnemyView = Backbone.View.extend({
        tagName: 'canvas',
        className: 'enemy',
        model: enemyModel,
        initialize: function () {
            this.listenTo(this.model, 'change', this.render);
            this.listenTo(this.model, 'add', this.show);
            this.ctx = this.el.getContext('2d');
            resources.load(['images/ship.png']);
            this.el.width = $('body').width();
            this.el.height = $('body').height();
        },
        render: function () {
            if (this.model.get('inGame')) {
                this.el.width = $('body').width();
                this.el.height = $('body').height();
                this.ctx.clearRect(0, 0, this.ctx.canvas.width, this.ctx.canvas.height);
                this.ctx.drawImage(resources.get('images/ship.png'), this.model.xpos, this.model.ypos, 70, 70);
            }
            return this;
        },
        move: function (enemy) {
            var x = enemy.get('targetX'),
                y = enemy.get('targetY');
        },
        motion: function () {
            this.ctx.clearRect(0, 0, this.ctx.canvas.width, this.ctx.canvas.height);
            this.ctx.drawImage(resources.get('images/ship.png'), this.model.xpos, this.model.ypos, 70, 70);
            this.ctx.restore();
            this.model.calculatePosition();
        }
    });

    return new EnemyView();

});