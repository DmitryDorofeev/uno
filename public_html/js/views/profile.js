define([
  'backbone',
  'tmpl/profile',
  'models/user'
], function (Backbone, tmpl, userModel) {
  var ProfileView = Backbone.View.extend({
    initialize: function() {
      this.render();
    },
    template: tmpl,
    render: function() {
      var profile = userModel.toJSON();
      this.$el.html(this.template(profile));
      return this;
    },
    show: function () {
      this.trigger('show', this);
    }
  });

  return new ProfileView();
});
