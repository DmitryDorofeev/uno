define([
  'jquery',
  'backbone',
  'models/score'
], function($, Backbone, ScoreModel){

  var ScoreCollection = Backbone.Collection.extend({
    initialize: function () {
        this.fetch({reset: true});
    },
    model: ScoreModel,
    url: '/api/v1/scoreboard'
  });

  return new ScoreCollection();
});
