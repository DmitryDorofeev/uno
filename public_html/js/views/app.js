define([
  'jquery',
  'backbone',
  'tmpl/app',
  'models/user',
  'models/game'
], function ($, Backbone, tmpl, userModel, gameView, gameModel) {

    var AppView = Backbone.View.extend({
        model: userModel,
        initialize: function() {
            this.$el = $('body');
            this.views = {};
            this.constructors = {};
            this.inLoad = false;
        },
        template: function () {
            return tmpl();
        },
        render: function () {
            this.$el.html(this.template());
            this.$error = this.$('.error');
            this.$errorText = this.$error.find('.js-text');
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
            this.trigger('stopall');
            this.hidePreloader();
            var view = this.views[name];
            if (view === undefined) {
                view = new this.constructors[name]();
                this.listenTo(view, 'show', this.hideOther);
                this.listenTo(view, 'load:start', this.showPreloader);
                this.listenTo(view, 'load:done', this.hidePreloader);
                this.listenTo(view, 'error', this.showError);
                view.render();
                this.$el.find('.app').append(view.$el);
                this.views[name] = view;
            }
            return view;
        },
        showPreloader: function (text) {
            this.inLoad = true;
            this.$el.find('.overlay').show();
            this.$el.find('.preloader').show();
            this.$('.js-text').text(text);
            this.$('.preload-text').show();
        },
        hidePreloader: function () {
            this.inLoad = false;
            this.$el.find('.overlay').hide();
            this.$el.find('.preloader').hide();
            this.$el.find('.preload-text').hide();
        },
        showError: function (message) {
            this.$errorText.text(message);
            this.$error.css({display: 'block'}).animate({top: 20, opacity: 1}, 500);
            setTimeout(_.bind(function () {
                this.$error.animate({top: 0, opacity: 0}, 500);
            }, this), 3000);
        }
    });

    return new AppView();

});
