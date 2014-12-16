require([
    'qunit',
    'tests/userModel',
    'tests/cards',
    'tests/game',
    'tests/gamemessages',
    'tests/gamemescards'
], function (qunit, userTest, cards, gameTest, gameMessages, gameMesCards) {
    userTest.run();
    cards.run();
    gameTest.run();
    gameMessages.run();
    gameMesCards.run();
    qunit.load();
    qunit.start();
});