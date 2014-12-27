require([
    'qunit',
    'tests/usermodeltests/userModel',
    'tests/cardmodeltests/cards',
    'tests/gamemodeltests/game',
    'tests/cardcollectiontests/cards',
], function (qunit, userTest, cards, gameTest, cardTest) {
    // userTest.run();
    cardTest.run();
    cards.run();
    gameTest.run();
    qunit.load();
    qunit.start();
});