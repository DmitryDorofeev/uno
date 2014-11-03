define([
  'jquery',
  'backbone'
], function($, Backbone) {
  var UserModel = Backbone.Model.extend({
    url: '/api/v1/profile',
    initialize: function() {
      this.fetch();
      _.bindAll(this, '_onSuccessLogin', '_onErrorLogin');
    },
    _onSuccessLogin: function (resp) {
      if (resp.status === 200) {
        this.set({
          'login': resp.login,
          'email': resp.email
        });
        this.trigger('login:ok');
      }
      else if (resp.status === 500) {
        this.trigger('login:bad', resp.message);
      }
    },
    _onErrorLogin: function() {
        this.trigger('login:error');
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
      $.ajax({
        url: '/api/v1/auth/signin',
        type: 'POST',
        data: data,
        dataType: 'json',
        success: this._onSuccessLogin,
        error: this._onErrorLogin
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
          else if (resp.status == 500) {
            that.trigger('signup:bad', resp.message);
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
