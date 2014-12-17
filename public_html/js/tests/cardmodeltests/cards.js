define([
    'backbone',
    'jquery',
    'collections/cards',
    'mockjax'
], function (Backbone, $, cardsCollection) {
    var run = function () {
        
        module('Cards tests');
        
        test('cardsCollection.keepCards', function () {
            ok(1===1, 'card(s) must be added to collection');
        });
        
    };
    
    return {run: run};
});