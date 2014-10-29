define([
  'jquery',
  'backbone',
  'tmpl/app',
  'models/user',
  'views/game'
], function ($, Backbone, tmpl, userModel, gameView) {

    var AppView = Backbone.View.extend({
        model: userModel,
        initialize: function() {
            this.$el = $('body');
            this.views = [];
        },
        template: tmpl,
        render: function () {
            this.$el.html(this.template());
            _.forEach(this.views, function (view) {
                this.$el.find('.app').append(view.$el);
            }, this);
        },
        subscribe: function (views) {
            if (views instanceof Array) {
                _.forEach(views, function (view) {
                    this.listenTo(view, 'show', this.add);
                    this.views.push(view);
                }, this);
            }
            else {
                this.listenTo(views, 'show', this.add);
                this.views.push(views);
            }
        },
        unsubscribe: function (view) {
            this.stopListening(view);
        },
        add: function (view) {
            _.forEach(this.views, function (v) {
                if (view !== v) {
                    v.hide();
                }
            });
        }
    });

    return new AppView();

});
