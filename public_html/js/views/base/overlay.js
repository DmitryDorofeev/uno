define([
    'jquery',
    'backbone',
    'tmpl/overlay'
], function ($, Backbone, tmpl) {

    /**
     * Base view for overlay dialogs
     * @constructor
     * @implements {Backbone}
     * @abstract
     */
    var OverlayView = Backbone.View.extend({

        baseEvents: {
            'click .overlay': 'close'
        },

        events: function () {
            return _.extend(this.baseEvents, this.customEvents);
        },

        initialize: function () {

        },

        overlay: function () {
            return tmpl();
        },

        render: function (params) {
            this.$el.html(this.template(params));
            this.$el.prepend(this.overlay());
            return this;
        },

        close: function () {
            this.$el.css('display', 'none');
        }

    });

    return OverlayView;
});
