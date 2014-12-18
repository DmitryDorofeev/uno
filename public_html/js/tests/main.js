require([
    'qunit',
    'tests/usermodeltests/userModel',
    'tests/cardmodeltests/cards',
    'tests/gamemodeltests/game'
], function (qunit, userTest, cards, gameTest) {
    userTest.run();
    cards.run();
    gameTest.run();
    qunit.load();
    qunit.start();
});