/*global js, Backbone, JST*/

js.Views = js.Views || {};

(function () {
    'use strict';

    js.Views.Toolbar = Backbone.View.extend({

        template: JST['app/scripts/templates/Toolbar.ejs'],

        tagName: 'div',

        id: '',

        className: '',

        events: {},

        initialize: function () {
            this.listenTo(this.model, 'change', this.render);
        },

        render: function () {
            this.$el.html(this.template(this.model.toJSON()));
        }

    });

})();
