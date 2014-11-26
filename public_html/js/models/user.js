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
        this.save();
    },
    login: function (data) {
        this.set(data);
        this.save();
    },
    signup: function(data) {
        this.set(data);
        this.save();
    }
  });
  
  return new UserModel();

});
