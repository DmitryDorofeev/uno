package testMechanics;

import base.GameMechanics;
import base.WebSocketService;
import mechanics.GameMechanicsImpl;

import static org.mockito.Mockito.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.junit.Assert.*;

/**
 * Created by К on 20.12.2014.
 */
public class TestGameMechanics {
    private WebSocketService testWebSocket = mock(WebSocketService.class);
    private GameMechanics testGameMechanics = new GameMechanicsImpl(testWebSocket);

    @BeforeClass
    public static void initForTest() {

    }

    @Test
    public void testAddUser() {
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

        testGameMechanics.addUser("test5gamer2", 5);
        testGameMechanics.addUser("test5gamer3", 5);
        testGameMechanics.addUser("test5gamer4", 5);
        testGameMechanics.addUser("test5gamer5", 5);
        assertEquals("Adding first user with 5 gamer(s)", false, testGameMechanics.isPlayerInWaiters("test2"));
        assertEquals("Adding second user with 5 gamer(s)", false, testGameMechanics.isPlayerInWaiters("test2gamer2"));
        assertEquals("Adding third user with 5 gamer(s)", false, testGameMechanics.isPlayerInWaiters("test2gamer3"));
        assertEquals("Adding forth user with 5 gamer(s)", false, testGameMechanics.isPlayerInWaiters("test2gamer4"));
        assertEquals("Adding fifth user with 5 gamer(s)", false, testGameMechanics.isPlayerInWaiters("test2gamer5"));

        testGameMechanics.addUser("test3", 3);
        testGameMechanics.addUser("test3", 3);
        assertEquals("Adding user with 3 gamer(s) and the same login", true, testGameMechanics.isPlayerInWaiters("test3"));

        testGameMechanics.addUser("test2", 2);
        assertEquals("Adding user with 2 gamer(s), player is playing", false, testGameMechanics.isPlayerInWaiters("test2"));

        assertEquals("User with 4 gamer(s) is still in waiters", true, testGameMechanics.isPlayerInWaiters("test5"));
    }
}
