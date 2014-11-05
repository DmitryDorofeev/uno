require([
    'qunit',
    'tests/userModel',
    'tests/cards'
], function (qunit, userTest, cards) {
    userTest.run();
    cards.run();
    qunit.load();
    qunit.start();
});