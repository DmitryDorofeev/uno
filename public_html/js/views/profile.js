define([
  'backbone',
  'tmpl/profile',
  'models/user'
], function (Backbone, tmpl, userModel) {
  var ProfileView = Backbone.View.extend({
    initialize: function() {
        this.listenTo(this.model, 'change', this.render);
    },
    events: {
        'click .js-logout': 'logout'
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
      this.$el.show();
    },
    hide: function () {
        this.$el.hide();
    },
    logout: function (event) {
        event.preventDefault();
        this.model.logout();
    }
  });

  return ProfileView;
});
