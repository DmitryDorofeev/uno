define([
    'jquery',
    'backbone',
    'userSync'
], function($, Backbone, userSync) {
  var UserModel = Backbone.Model.extend({
    loginUrl: '/api/v1/auth/signin',
    signupUrl: '/api/v1/auth/signup',
    logoutUrl: '/api/v1/auth/logout',
    profileUrl: '/api/v1/profile',
    initialize: function() {
      this.fetch();
    },
    sync: userSync,
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
