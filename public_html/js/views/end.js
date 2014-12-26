define([
    'backbone',
    'models/game',
    'tmpl/end'
], function (Backbone, gameModel, tmpl) {
    var EndView = Backbone.View.extend({
        initialize: function () {
            this.listenTo(gameModel, 'message:end', this.show);
        },
        template: function (ctx) {
            return tmpl(ctx);
        },
        render: function (msg) {
            this.$el.html(this.template(msg));
            return this;
        },
        show: function (msg) {
            debugger;
            this.render(this.template(msg));
            this.$el.show();
        }
    });

    return new EndView();
});