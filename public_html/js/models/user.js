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
    },
    sync: function(method, model, options) {
        var methodMap = {
            'create': {
                method: 'POST',
                url: model.signupUrl,
                success: function (resp) {
                    if (resp.status == 200) {
                        model.clear();
                        model.trigger('signup:ok');
                    }
                    else if (resp.status == 500) {
                        model.trigger('signup:bad', resp.message);
                    }
                },
                error: function () {
                    model.trigger('signup:error');
                }
            },
            'read': {
                method: 'GET',
                url: model.profileUrl,
                success: function (resp) {
                    if (resp.status === 200) {
                        model.set({
                            'login': resp.login,
                            'email': resp.email,
                            'isLogined': true
                        });
                        model.trigger('login:ok');
                    }
                }
            },
            'update': {
                method: 'POST',
                url: model.has('password') ? model.loginUrl : model.logoutUrl,
                success: function (resp) {
                    if (model.has('password')) {
                        if (resp.status === 200) {
                            model.set({
                                'login': resp.login,
                                'email': resp.email,
                                'isLogined': true
                            });
                            model.unset('password');
                            model.trigger('login:ok');
                            console.log(model.toJSON());
                        }
                        else if (resp.status === 500) {
                            model.trigger('login:bad', resp.message);
                        }
                    }
                    else {
                        model.clear();
                        model.trigger('logout');
                    }
                },
                error: function () {
                    if (model.has('password')) {
                        model.trigger('login:error');
                    }
                    else {
                        model.trigger('logout:error');
                    }
                }
            }
        };
        
        var type = methodMap[method].method,
            url = methodMap[method].url,
            success = methodMap[method].success,
            error = methodMap[method].error;
        
        var xhr = $.ajax({
            type: type,
            url: url,
            data: (model instanceof Backbone.Model) ? model.toJSON() : {},
            success: success,
            error: error,
            dataType: 'json'
        });
    },
    isLogined: function() {
      return (this.has('isLogined'));
    },
    logout: function() {
        this.sync('update', this);
    },
    login: function (data) {
        this.set(data);
        this.sync('update', this);
    },
    signup: function(data) {
        this.set(data);
        this.sync('create', this);
    }
  });
  
  return new UserModel();

});
