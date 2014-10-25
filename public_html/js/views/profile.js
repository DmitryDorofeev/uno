define([
  'backbone',
  'tmpl/profile',
  'models/user'
], function (Backbone, tmpl, userModel) {
  var ProfileView = Backbone.View.extend({
    initialize: function() {
      this.render();
    },
    model: userModel,
    template: function () {
        return tmpl(this.model.toJSON());
    },
    render: function() {
      this.$el.html(this.template());
      return this;
    },
    show: function () {
      this.trigger('show', this);
    }
  });

  return new ProfileView();
});
