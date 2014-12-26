require([
    'qunit',
    'tests/usermodeltests/userModel',
    'tests/cardmodeltests/cards',
    'tests/gamemodeltests/game',
    'tests/cardcollectiontests/cards'
], function (qunit, userTest, cards, gameTest, cardTest) {
    userTest.run();
    cards.run();
    gameTest.run();
    cardTest.run();
    qunit.load();
    qunit.start();
});