define([
    'jquery',
    'backbone'
], function($, Backbone) {
  var UserModel = Backbone.Model.extend({
    loginUrl: '/api/v1/auth/signin',
    signupUrl: '/api/v1/auth/signup',
    logoutUrl: '/api/v1/auth/logout',
    profileUrl: '/api/v1/profile',
    initialize: function() {
      this.fetch();
      _.bindAll(this, '_onSuccessLogin', '_onErrorLogin', '_onSuccessLogout', '_onSuccessSignup', '_onErrorSignup');
    },
    
    // BACKBONE SYNC
    sync: function(method, model, options) {
        var methodMap = {
            'create': {
                method: 'POST',
                url: this.signupUrl
            },
            'read': {
                method: 'GET',
                url: this.profileUrl
            },
            'update': {
                method: 'POST',
                url: model.get('password') ? this.loginUrl : this.logoutUrl
            }
        };
        
        var type = methodMap[method].method,
            url = methodMap[method].url;
        
        var xhr = $.ajax({
            type: type,
            url: url,
            data: (model instanceof Backbone.Model) ? model.toJSON() : {},
            success: function(resp) {
                if (method !== 'read') {
                    model.clear();  
                }
                options.success(resp);
            },
            error: options.error ? options.error : function () {},
            dataType: 'json'
        });
    },
    
    // CALLBACKS
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
    _onSuccessLogout: function () {
        this.trigger('logout');
    },
    _onSuccessSignup: function(resp) {
      if (resp.status == 200) {
        this.trigger('signup:ok');
      }
      else if (resp.status == 500) {
        this.trigger('signup:bad', resp.message);
      }
    },
    _onErrorSignup: function() {
      this.trigger('signup:error');
    },
    // END CALLBACKS
    
    isLogined: function() {
      return (this.get('login') !== undefined);
    },
    logout: function() {
        this.sync('update', this, {success: this._onSuccessLogout});
    },
    login: function (data) {
        this.set(data);
        this.sync('update', this, {success: this._onSuccessLogin, error: this._onErrorLogin});
    },
    signup: function(data) {
        this.set(data);
        this.sync('create', this, {success: this._onSuccessSignup, error: this._onErrorSignup});
    }
  });
  
  return new UserModel();

});
