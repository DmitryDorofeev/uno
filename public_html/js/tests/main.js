require([
    'qunit',
    'tests/userModel'
], function (qunit, userTest) {
    userTest.run();
    qunit.load();
    qunit.start();
});