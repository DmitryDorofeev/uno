define([
    'jquery',
    'backbone',
    'userSync'
], function($, Backbone, userSync) {
  var UserModel = Backbone.Model.extend({
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
