define([
  'jquery',
  'backbone',
  'tmpl/main',
  'models/user'
], function($, Backbone, tmpl, userModel) {

  var homeView = Backbone.View.extend({
    className: 'main-page',
    initialize: function() {
      this.render();
    },
    template: tmpl,
    render: function() {
      this.$el.html(this.template());
      return this;
    },
    show: function() {
      this.trigger('show', this);
    }
  });

  return new homeView();

});
