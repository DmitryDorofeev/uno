define([
  'jquery',
  'backbone',
  'models/score',
  'scoreSync'
], function($, Backbone, ScoreModel, scoreSync){

  var ScoreCollection = Backbone.Collection.extend({
    sync: scoreSync,
    initialize: function () {
    },
    model: ScoreModel
  });

  return new ScoreCollection();
});
