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
            this.constructors = {};
        },
        template: tmpl,
        render: function () {
            this.$el.html(this.template());
        },
        register: function (views) {
            _.forEach(views, function (view, name) {
                this.constructors[name] = view;
            }, this);
        },
        hideOther: function (view) {
            _.forEach(this.views, function (v) {
                if (view !== v) {
                    v.hide();
                }
            });
        },
        getView: function (name) {
            var view = this.views[name];
            if (view === undefined) {
                view = new this.constructors[name]();
                this.listenTo(view, 'show', this.hideOther);
                this.listenTo(view, 'load:start', this.showPreloader);
                this.listenTo(view, 'load:done', this.hidePreloader);
                view.render();
                this.$el.find('.app').append(view.$el);
                this.views[name] = view;
            }
            return view;
        },
        showPreloader: function () {
            this.$el.find('.overlay').show();
            this.$el.find('.preloader').show();
        },
        hidePreloader: function () {
            this.$el.find('.overlay').hide();
            this.$el.find('.preloader').hide();
        }
    });

    return new AppView();

});
