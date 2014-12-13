require([
    'qunit',
    'tests/userModel',
    'tests/cards',
    'tests/game'
], function (qunit, userTest, cards, gameTest) {
    userTest.run();
    cards.run();
    gameTest.run();
    qunit.load();
    qunit.start();
});