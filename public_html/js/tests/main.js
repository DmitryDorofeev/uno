require([
    'qunit',
    'tests/usermodeltests/userModel',
    'tests/cardmodeltests/cards',
    'tests/gamemodeltests/game'
], function (qunit, userTest, cards, gameTest) {
    gameTest.run();
    userTest.run();
    cards.run();
    qunit.load();
    qunit.start();
});