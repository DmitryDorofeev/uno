/**
 * Created by dmitry on 09.10.14.
 */
define([
  'jquery',
  'backbone',
  'models/user',
  'tmpl/panel'
], function ($, Backbone, userModel, tmpl) {
  var PanelView = Backbone.View.extend({
    tagName: 'div',
    className: 'panel',
    model: userModel,
    events: {
      'click .js-logout': 'logout'
    },
    template: function () {
      return tmpl(this.model);
    },
    initialize: function () {
        this.listenTo(this.model, 'change', this.render);
        this.render();
    },
    render: function () {
        this.$el.html(this.template());
        if (this.model.isLogined()) {
          this.$el.addClass('panel_logined');
        }
        return this;
    },
    logout: function () {
      userModel.logout();
    }
  });
  
  return new PanelView();
});