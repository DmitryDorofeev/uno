define([
    'jquery',
    'backbone',
    'collections/score',
    'tmpl/scoreboard'
], function ($, Backbone, scoreCollection, tmpl) {

    var ScoreboardView = Backbone.View.extend({
        tagName: 'div',
        className: 'scoreboard',
        collection: scoreCollection,
        initialize: function () {
            this.listenTo(this.collection, 'reset', this.insertInfo);
            this.collection.comparator = function (score) {
                return -score.get('score');
            };
        },
        template: function () {
            return tmpl(this.collection.toJSON());
        },
        render: function () {
            this.collection.fetch({reset: true});
            return this;
        },
        show: function () {
            this.trigger('show', this);
        },
        insertInfo: function () {
            this.collection.sort();
            this.$el.html(this.template());
        }
    });

    return new ScoreboardView();
});
