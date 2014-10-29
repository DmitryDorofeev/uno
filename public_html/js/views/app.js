define([
  'jquery',
  'backbone',
  'tmpl/app',
  'models/user',
  'views/game'
], function ($, Backbone, tmpl, userModel, gameView) {

    var AppView = Backbone.View.extend({
        className: 'app',
        model: userModel,
        initialize: function() {
            this.$container = $('body');
            this.views = [];
        },
        template: tmpl,
        render: function () {
            this.$el.html(this.template());
            this.$container.html(this.$el);
            _.forEach(this.views, function (view) {
                this.$el.append(view.$el);
            }, this);
        },
        subscribe: function (views) {
            if (views instanceof Array) {
                for (var i in views) {
                    this.listenTo(views[i], 'show', this.add);
                    this.views.push(views[i]);
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
            for (var i in this.views) {
                if (view !== this.views[i]) {
                    this.views[i].hide();
                }
            }
        }
    });

    return new AppView();

});
