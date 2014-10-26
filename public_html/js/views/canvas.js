define([
    'backbone'
], function (Backbone) {
    
    var CanvasView = Backbone.View.extend({
        tagName: 'canvas',
        initialize: function () {
            
        },
        render: function () {
            
        },
        show: function () {
            this.trigger('show', this);
        }
    });
    
    return new CanvasView();
});