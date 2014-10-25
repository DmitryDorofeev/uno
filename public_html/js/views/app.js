define([
  'jquery',
  'backbone',
  'tmpl/app',
  'models/user',
  'views/game'
], function ($, Backbone, tmpl, userModel, gameView) {

    var AppView = Backbone.View.extend({
        tagName: 'div',
        className: 'app',
        model: userModel,
        initialize: function() {
            this.$container = $('body');
            this.render();
        },
        template: tmpl,
        render: function () {
            this.$el.html(this.template());
            this.$container.html(this.$el);
        },
        subscribe: function (views) {
            if (views instanceof Array) {
                for (var i in views) {
                    this.listenTo(views[i], 'show', this.add);
                }
            }
            else {
                this.listenTo(views, 'show', this.add);
            }
        },
        unsubscribe: function (view) {
            this.stopListening(view);
        },
        add: function (view) {
            this.$el.find('#page').html(view.$el);
        }
    });

    return new AppView();

});
