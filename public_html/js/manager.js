define([
  'underscore',
  'backbone'
], function (_, Backbone, homeView) {
  var manager = _.extend({
    $page: $('#page'),
    subscribe: function (view) {
      this.listenTo(view, 'show', this.add);
      this.view = view;
    },
    unsubscribe: function (view) {
      this.stopListening(view);
    },
    add: function () {
      this.$page.html(this.view.render().$el);
    }
  }, Backbone.Events);
  
  return manager;
  
});