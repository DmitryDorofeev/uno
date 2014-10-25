define([
  'jquery',
  'backbone',
  'models/score'
], function($, Backbone, ScoreModel){

  var ScoreCollection = Backbone.Collection.extend({
    model: ScoreModel,
    url: '/api/v1/scoreboard'
  });

  return new ScoreCollection();
});
