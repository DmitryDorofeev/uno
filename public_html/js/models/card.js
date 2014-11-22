define([
    'backbone'
], function (Backbone) {
    var CardModel = Backbone.Model.extend({
        initialize: function () {
        },
        select: function () {
            this.collection.sendCard(this);
        }
    });

    return CardModel;
});
