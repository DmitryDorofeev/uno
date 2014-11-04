define([
], function () {
    var run = function () {
        test('assertions', function () {
            ok(1 === 1, '1 equals 1');
        });
    };
    
    return {run: run};
});