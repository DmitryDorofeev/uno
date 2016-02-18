define([
  'backbone',
  'tmpl/all',
  'models/user'
], function (Backbone, template, userModel) {

  var ProfileView = Backbone.View.extend({

    initialize: function() {
        this.listenTo(this.model, 'change', this.render);
    },

    model: userModel,

    template: function () {
        return template({
            block: 'profile',
            user: this.model.toJSON()
        });
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
    }

  });

  return ProfileView;
});
