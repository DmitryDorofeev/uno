define([
  'jquery',
  'backbone',
  'tmpl/app',
  'models/user'
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
                this.listenTo(this.views[name], 'load:start', this.showPreloader);
                this.listenTo(this.views[name], 'load:done', this.hidePreloader);
                this.views[name].render();
                this.$el.find('.app').append(this.views[name].$el);
            }
            return this.views[name];
        },
        showPreloader: function () {
            $('.overlay').show();
            $('.preloader').show();
        },
        hidePreloader: function () {
            this.$el.find('.overlay').hide();
            this.$el.find('.preloader').hide();
        }
    });

    return new AppView();

});
