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
            this.views = {};
        },
        template: tmpl,
        render: function () {
            this.$el.html(this.template());
        },
        register: function (views) {
            _.forEach(views, function (view, name) {
                this.listenTo(view, 'show', this.hideOther);
                this.views[name] = view;
                this.$el.find('.app').append(view.$el);
            }, this);
        },
        hideOther: function (view) {
            console.log(this.views);
            _.forEach(this.views, function (v) {
                if (view !== v) {
                    v.hide();
                }
            });
        },
        getView: function (name, ViewConstructor) {
            if (this.views[name] === undefined) {
                this.views[name] = new ViewConstructor();
                this.listenTo(this.views[name], 'show', this.hideOther);
                this.$el.find('.app').append(this.views[name].$el);
            }
            return this.views[name];
        }
    });

    return new AppView();

});
