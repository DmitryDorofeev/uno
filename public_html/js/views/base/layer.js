define([
    'jquery',
    'backbone',
    'tmpl/all'
], function ($, Backbone, template) {

    /**
     * Base view for overlay dialogs
     * @constructor
     * @implements {Backbone}
     * @abstract
     */
    var LayerView = Backbone.View.extend({

        baseEvents: {
            'click .overlay': 'close'
        },

        events: function () {
            return _.extend(this.baseEvents, this.customEvents);
        },

        initialize: function () {

        },

        template: function () {
            return template({ block: 'layer' });
        },

        render: function (params) {
            this.$el.html(this.template(params));
            this.$el.prepend(this.template());
            return this;
        },

        close: function () {
            this.$el.css('display', 'none');
        }

    });

    return LayerView;
});
