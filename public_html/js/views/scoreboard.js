define([
    'jquery',
    'backbone',
    'collections/score',
    'tmpl/scoreboard'
], function ($, Backbone, scoreCollection, tmpl) {

    var ScoreboardView = Backbone.View.extend({
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
            this.trigger('load:start', 'Загрузка рейтинга');
            return this;
        },
        show: function () {
            this.trigger('show', this);
            this.$el.show();
        },
        hide: function () {
            this.$el.hide();
        },
        insertInfo: function () {
            setTimeout(function () {
                this.collection.sort();
                this.$el.html(this.template());
                this.trigger('load:done');
            }.bind(this), 2000)
            
        }
    });

    return ScoreboardView;
});
