require([
    'qunit',
    'tests/sampleTest'
], function (qunit, test) {
    test.run();
    qunit.load();
    qunit.start();
});