package testMechanics;

import base.GameMechanics;
import base.WebSocketService;
import mechanics.GameMechanicsImpl;

import static org.mockito.Mockito.*;

import mechanics.GameUser;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by К on 20.12.2014.
 */
public class TestGameMechanics {
    private WebSocketService testWebSocket = mock(WebSocketService.class);
    private GameMechanics testGameMechanics = new GameMechanicsImpl(testWebSocket);
    ArrayList<GameUser> test2Players = new ArrayList<>();
    ArrayList<GameUser> test5Players = new ArrayList<>();

    @BeforeClass
    public static void initForTest() {
        //А НИЧЕГО!
    }

    @Test
    public void testAddUserAndStartGame() {
        GameUser test2 = new GameUser("test2", 2);
        test2Players.add(test2);
        GameUser test2gamer2 = new GameUser("test2gamer2", 2);
        test2Players.add(test2gamer2);

        GameUser test5 = new GameUser("test5", 5);
        test5Players.add(test5);
        GameUser test5gamer2 = new GameUser("test5gamer2", 5);
        test5Players.add(test5gamer2);
        GameUser test5gamer3 = new GameUser("test5gamer3", 5);
        test5Players.add(test5gamer3);
        GameUser test5gamer4 = new GameUser("test5gamer4", 5);
        test5Players.add(test5gamer4);
        GameUser test5gamer5 = new GameUser("test5gamer5", 5);
        test5Players.add(test5gamer5);

        testGameMechanics.addUser("test0", 0);
        assertEquals("Adding user with 0 gamer(s)", false, testGameMechanics.isPlayerInWaiters("test0"));
        testGameMechanics.addUser("test1", 1);
        assertEquals("Adding user with 1 gamer(s)", false, testGameMechanics.isPlayerInWaiters("test1"));

        testGameMechanics.addUser("test2", 2);
        assertEquals("Adding user with 2 gamer(s)", true, testGameMechanics.isPlayerInWaiters("test2"));
        testGameMechanics.addUser("test3", 3);
        assertEquals("Adding user with 3 gamer(s)", true, testGameMechanics.isPlayerInWaiters("test3"));
        testGameMechanics.addUser("test4", 4);
        assertEquals("Adding user with 4 gamer(s)", true, testGameMechanics.isPlayerInWaiters("test4"));
        testGameMechanics.addUser("test5", 5);
        assertEquals("Adding user with 5 gamer(s)", true, testGameMechanics.isPlayerInWaiters("test5"));

        testGameMechanics.addUser("test6", 6);
        assertEquals("Adding user with 6 gamer(s)", false, testGameMechanics.isPlayerInWaiters("test6"));

        testGameMechanics.addUser("test2gamer2", 2);
        assertEquals("Adding first user with 2 gamer(s) to game", false, testGameMechanics.isPlayerInWaiters("test2"));
        assertEquals("Adding second user with 2 gamer(s) to game", false, testGameMechanics.isPlayerInWaiters("test2gamer2"));
        verify(testWebSocket).notifyStartGame(test2);
        verify(testWebSocket).notifyStartGame(test2gamer2);
        verify(testWebSocket).sendStartCards(test2);
        verify(testWebSocket).sendStartCards(test2gamer2);
        verify(testWebSocket).notifyGameStep(true, "OK", test2);
        verify(testWebSocket).notifyGameStep(true, "OK", test2gamer2);

        testGameMechanics.addUser("test5gamer2", 5);
        testGameMechanics.addUser("test5gamer3", 5);
        testGameMechanics.addUser("test5gamer4", 5);
        testGameMechanics.addUser("test5gamer5", 5);
        assertEquals("Adding first user with 5 gamer(s)", false, testGameMechanics.isPlayerInWaiters("test2"));
        assertEquals("Adding second user with 5 gamer(s)", false, testGameMechanics.isPlayerInWaiters("test2gamer2"));
        assertEquals("Adding third user with 5 gamer(s)", false, testGameMechanics.isPlayerInWaiters("test2gamer3"));
        assertEquals("Adding forth user with 5 gamer(s)", false, testGameMechanics.isPlayerInWaiters("test2gamer4"));
        assertEquals("Adding fifth user with 5 gamer(s)", false, testGameMechanics.isPlayerInWaiters("test2gamer5"));
        verify(testWebSocket).notifyStartGame(test5);
        verify(testWebSocket).notifyStartGame(test5gamer2);
        verify(testWebSocket).notifyStartGame(test5gamer3);
        verify(testWebSocket).notifyStartGame(test5gamer4);
        verify(testWebSocket).notifyStartGame(test5gamer5);
        verify(testWebSocket).sendStartCards(test5);
        verify(testWebSocket).sendStartCards(test5gamer2);
        verify(testWebSocket).sendStartCards(test5gamer3);
        verify(testWebSocket).sendStartCards(test5gamer4);
        verify(testWebSocket).sendStartCards(test5gamer5);
        verify(testWebSocket).notifyGameStep(true, "OK", test5);
        verify(testWebSocket).notifyGameStep(true, "OK", test5gamer2);
        verify(testWebSocket).notifyGameStep(true, "OK", test5gamer3);
        verify(testWebSocket).notifyGameStep(true, "OK", test5gamer4);
        verify(testWebSocket).notifyGameStep(true, "OK", test5gamer5);

        testGameMechanics.addUser("test3", 3);
        testGameMechanics.addUser("test3", 3);
        assertEquals("Adding user with 3 gamer(s) and the same login", true, testGameMechanics.isPlayerInWaiters("test3"));

        testGameMechanics.addUser("test2", 2);
        assertEquals("Adding user with 2 gamer(s), player is playing", false, testGameMechanics.isPlayerInWaiters("test2"));

        assertEquals("User with 4 gamer(s) is still in waiters", true, testGameMechanics.isPlayerInWaiters("test5"));
    }

    @Test
    public void testGameStep () {

        GameUser test5 = new GameUser("test5", 5);
        test5Players.add(test5);
        GameUser test5gamer2 = new GameUser("test5gamer2", 5);
        test5Players.add(test5gamer2);
        GameUser test5gamer3 = new GameUser("test5gamer3", 5);
        test5Players.add(test5gamer3);
        GameUser test5gamer4 = new GameUser("test5gamer4", 5);
        test5Players.add(test5gamer4);
        GameUser test5gamer5 = new GameUser("test5gamer5", 5);
        test5Players.add(test5gamer5);

        testGameMechanics.addUser("test5", 5);
        testGameMechanics.addUser("test5gamer2", 5);
        testGameMechanics.addUser("test5gamer3", 5);
        testGameMechanics.addUser("test5gamer4", 5);
        testGameMechanics.addUser("test5gamer5", 5);

        testGameMechanics.gameStep(test5.getMyName(), -1, "red");
        verify(testWebSocket).notifyGameStep(false, "Not your turn!", test5);

        testGameMechanics.gameStep(test5.getMyName(), 1, "red");
        testGameMechanics.gameStep(test5gamer2.getMyName(), 1, "red");
        testGameMechanics.gameStep(test5gamer3.getMyName(), 2, "red");
        testGameMechanics.gameStep(test5gamer4.getMyName(), 0, "yellow");
        verify(testWebSocket).notifyGameStep(false, "You can not put this card!", test5gamer3);

        testGameMechanics.gameStep(test5gamer4.getMyName(), 2, "yellow");
        testGameMechanics.gameStep(test5gamer5.getMyName(), 1, "yellow");
        testGameMechanics.gameStep(test5gamer4.getMyName(), 1, "yellow");
        testGameMechanics.gameStep(test5gamer4.getMyName(), 1, "green");
        verify(testWebSocket).notifyGameStep(false, "Not your turn!", test5gamer4);

        testGameMechanics.gameStep(test5.getMyName(), 0, "green");
        testGameMechanics.gameStep(test5gamer2.getMyName(), 0, "yellow");
        verify(testWebSocket).notifyGameStep(false, "Player has not that card!", test5gamer2);
    }

    @Test
    public void testInitJoystick () {
        GameUser test5 = new GameUser("test5", 5);
        test5Players.add(test5);
        GameUser test5gamer2 = new GameUser("test5gamer2", 5);
        test5Players.add(test5gamer2);
        GameUser test5gamer3 = new GameUser("test5gamer3", 5);
        test5Players.add(test5gamer3);
        GameUser test5gamer4 = new GameUser("test5gamer4", 5);
        test5Players.add(test5gamer4);
        GameUser test5gamer5 = new GameUser("test5gamer5", 5);
        test5Players.add(test5gamer5);

        GameUser test2 = new GameUser("test2", 2);

        testGameMechanics.addUser("test5", 5);
        testGameMechanics.addUser("test5gamer2", 5);
        testGameMechanics.addUser("test5gamer3", 5);
        testGameMechanics.addUser("test5gamer4", 5);
        testGameMechanics.addUser("test5gamer5", 5);

        testGameMechanics.initJoystick("test5");
        verify(testWebSocket).sendCardsToJoystick(true, "OK", test5.getMyName(), test5.getFocusOnCard(), test5.getCards());
        testGameMechanics.initJoystick("test2");
        verify(testWebSocket).sendCardsToJoystick(false, "Player has not started game yet", test2.getMyName(), -1, null);
    }

    @Test
    public void testStepByJoystick () {
        ArgumentCaptor<GameUser> playerArgumentCaptor = ArgumentCaptor.forClass(GameUser.class);
        GameUser test5 = new GameUser("test5", 5);
        test5Players.add(test5);
        GameUser test5gamer2 = new GameUser("test5gamer2", 5);
        test5Players.add(test5gamer2);
        GameUser test5gamer3 = new GameUser("test5gamer3", 5);
        test5Players.add(test5gamer3);
        GameUser test5gamer4 = new GameUser("test5gamer4", 5);
        test5Players.add(test5gamer4);
        GameUser test5gamer5 = new GameUser("test5gamer5", 5);
        test5Players.add(test5gamer5);

        testGameMechanics.addUser("test5", 5);
        testGameMechanics.addUser("test5gamer2", 5);
        testGameMechanics.addUser("test5gamer3", 5);
        testGameMechanics.addUser("test5gamer4", 5);
        testGameMechanics.addUser("test5gamer5", 5);

        long prevFocus = test5.getFocusedCardId();
        testGameMechanics.stepByJoystick("test5", "selectRightCard", "red");
        verify(testWebSocket).notifyChangeFocus(playerArgumentCaptor.capture());
        assertEquals("Right card was selected", prevFocus + 1, playerArgumentCaptor.getValue().getFocusedCardId());

        prevFocus = test5.getFocusOnCard();
        testGameMechanics.stepByJoystick("test5", "selectLeftCard", "red");
        verify(testWebSocket).notifyChangeFocus(playerArgumentCaptor.capture());
        assertEquals("Right card was selected", prevFocus - 1, playerArgumentCaptor.getValue().getFocusedCardId());

        testGameMechanics.stepByJoystick("test5gamer3", "throwCard", "red");
        verify(testWebSocket).notifyGameStep(false, "Not your turn!", test5gamer3);
    }
}
