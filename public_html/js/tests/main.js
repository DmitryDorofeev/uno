require([
    'qunit',
    'tests/usermodeltests/userModel',
    'tests/cardmodeltests/cards',
    'tests/gamemodeltests/game',
    'tests/gamemodeltests/gamemessages',
    'tests/gamemodeltests/gamemescards'
], function (qunit, userTest, cards, gameTest, gameMessages, gameMesCards) {
    userTest.run();
    cards.run();
    gameTest.run();
    gameMessages.run();
    gameMesCards.run();
    qunit.load();
    qunit.start();
});