define([
  'jquery',
  'backbone'
], function($, Backbone) {
  var UserModel = Backbone.Model.extend({
    url: '/api/v1/auth/signin',
    initialize: function() {
      this.fetch();
    },
    isLogined: function() {
      return (this.get('login') !== undefined);
    },
    logout: function() {
      var that = this;
      $.ajax({
        type: 'POST',
        url: '/api/v1/auth/logout'
      }).done(function() {
        that.clear();
        that.trigger('logout');
      });
    },
    login: function (data) {
      var that = this;
      $.ajax({
        url: this.url,
        type: 'POST',
        data: data,
        dataType: 'json',
        success: function(resp) {
          if (resp.status === 200) {
            that.set({
              'login': resp.login,
              'email': resp.email,
              'avatar': resp.avatar
            });
            that.trigger('login:ok');
          }
          else if (resp.status === 403) {
            that.trigger('login:bad');
          }
        },
        error: function() {
          that.trigger('login:error');
        }
      });
    },
    signup: function(data) {
      var that = this;
      $.ajax({
        url: '/api/v1/auth/signup',
        type: 'POST',
        data: data,
        dataType: 'json',
        success: function(resp) {
          if (resp.status == 200) {
            that.set(resp);
            that.trigger('signup:ok');
          }
          else if (resp.status == 404) {
            that.trigger('signup:bad');
          }
        },
        error: function() {
          that.trigger('signup:error');
        }
      });
    }
  });

  return new UserModel();

});
