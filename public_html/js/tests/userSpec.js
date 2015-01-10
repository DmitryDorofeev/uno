define(function (require) {

  var Backbone = require('backbone'),
      userModel = require('models/user');

  var listener = {};
  _.extend(listener, Backbone.Events);

  describe('User model', function () {
    it('must ', function () {
      expect(true).toBe(true);
    });
  });

});
