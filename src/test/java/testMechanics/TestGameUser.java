package testMechanics;

import mechanics.GameSession;
import mechanics.GameUser;
import org.junit.BeforeClass;
import org.junit.Test;
import resources.CardResource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by К on 22.12.2014.
 */
public class TestGameUser {
    private GameUser testGameUser = new GameUser("test", 5);
    public static List<CardResource> testCards = new ArrayList<>();
    public static List<CardResource> testNewCards = new ArrayList<>();
    public GameSession testGameSession = mock(GameSession.class);

    @BeforeClass
    public static void initTestValues() {
        testCards.add(new CardResource(105, "black", "incFor", 14, 240, 360, 2880, 1440));
        testCards.add(new CardResource(101, "black", "color", 13, 240, 360, 2880, 0));
        testCards.add(new CardResource(2, "yellow", "number", 0, 240, 360, 0, 360));
        testCards.add(new CardResource(3, "green", "reverse", 11, 240, 360, 2400, 720));

        testNewCards.add(new CardResource(80, "blue", "skip", 10, 240, 360, 2160, 1080));
        testNewCards.add(new CardResource(13, "red", "number", 2, 240, 360, 480, 0));
    }

    @Test
    public void testCanDeleteCardAndDeletingCards() {
        long prevFocusOnCard;
        testGameUser.setGameSession(testGameSession);
        testGameUser.setCards(testCards);

        assertEquals("Deleting existing special colored card", true, testGameUser.canDeleteCard(testCards.get(3)));
        prevFocusOnCard = testCards.size() - 1;
        testGameUser.setFocusOnCard(prevFocusOnCard);
        testGameUser.deleteCard(testCards.get(3));
        assertEquals("Deleting existing special colored card (deleteCard)", prevFocusOnCard - 1, testGameUser.getFocusOnCard());

        assertEquals("Deleting existing number card", true, testGameUser.canDeleteCard(testCards.get(2)));
        prevFocusOnCard--;
        testGameUser.setFocusOnCard(prevFocusOnCard);
        testGameUser.deleteCard(testCards.get(2));
        assertEquals("Deleting existing number card (deleteCard)", prevFocusOnCard - 1, testGameUser.getFocusOnCard());

        assertEquals("Deleting non-existing number card", false, testGameUser.canDeleteCard(new CardResource(16, "yellow", "number", 0, 240, 360, 480, 1080)));
        prevFocusOnCard--;
        testGameUser.deleteCard(new CardResource(16, "yellow", "number", 0, 240, 360, 480, 1080));
        assertEquals("Deleting non-existing number card (deleteCard)", prevFocusOnCard, testGameUser.getFocusOnCard());

        assertEquals("Deleting existing color-card", true, testGameUser.canDeleteCard(testCards.get(1)));
        testGameUser.setFocusOnCard(prevFocusOnCard);
        testGameUser.deleteCard(testCards.get(1));
        verify(testGameSession).setUnoAction();
        assertEquals("Deleting existing special color-card (deleteCard)", prevFocusOnCard - 1, testGameUser.getFocusOnCard());


        assertEquals("Deleting existing incFor card", true, testGameUser.canDeleteCard(testCards.get(0)));
        prevFocusOnCard--;
        testGameUser.setFocusOnCard(prevFocusOnCard);
        testGameUser.deleteCard(testCards.get(0));
        assertEquals("Deleting existing incFor card (deleteCard)", prevFocusOnCard - 1, testGameUser.getFocusOnCard());
    }

    @Test
    public void testUpdateFocusOnCard() {
        testGameUser.setCards(testCards);
        testGameUser.setFocusOnCard(testCards.size() - 1);

        testGameUser.updateFocusOnCard("right");
        assertEquals("Changing direction to right", 0, testGameUser.getFocusOnCard());

        testGameUser.updateFocusOnCard("left");
        assertEquals("Changing direction to left", testCards.size() - 1, testGameUser.getFocusOnCard());

        testGameUser.updateFocusOnCard("none");
        assertEquals("No changing direction", testCards.size() - 1, testGameUser.getFocusOnCard());
    }

    @Test
    public void testSavingNewCards() {
        testGameUser.setCards(testCards);
        testGameUser.addCards(testNewCards);

        for (int i = 0; i < testGameUser.getNewCards().size(); ++i) {
            assertEquals("New cards has added in new cards' list", testNewCards.get(i), testGameUser.getNewCards().get(i + testCards.size()));
            assertEquals("New cards has added in all cards' list", testNewCards.get(i), testGameUser.getCards().get(i + testCards.size()));
        }
    }
}
