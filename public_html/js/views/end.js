define([
    'backbone',
    'models/game',
    'tmpl/end'
], function (Backbone, gameModel, tmpl) {
    var EndView = Backbone.View.extend({
        initialize: function () {
            this.listenTo(gameModel, 'message:end', this.render);
        },
        template: function () {
            return tmpl();
        },
        render: function () {
            this.$el.html(this.template());
            return this;
        }
    });

    return new EndView();
});