package testMechanics;

import mechanics.GameUser;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import resources.CardResource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
/**
 * Created by К on 22.12.2014.
 */
public class TestGameUser {
    private GameUser testGameUser = new GameUser("test", 5);
    List<CardResource> testCards = new ArrayList<>();

    @BeforeClass
    public void initTestValues() {
        testCards.add(new CardResource(105, "black", "incFor", 14, 240, 360, 2880, 1440));
        testCards.add(new CardResource(101, "black", "color", 13, 240, 360, 2880, 0));
        testCards.add(new CardResource(2, "yellow", "number", 0, 240, 360, 0, 360));
        testCards.add(new CardResource(3, "green", "reverse", 11, 240, 360, 2400, 720));
    }

    @Test
    public void testCanDeleteCardAndDeletingCards() {
        long prevFocusOnCard;
        testGameUser.setCards(testCards);

        assertEquals("Deleting existing number card", true, testGameUser.canDeleteCard(testCards.get(2)));
        prevFocusOnCard = testCards.size();
        testGameUser.deleteCard(testCards.get(2));
        assertEquals("Deleting existing number card (deleteCard)", prevFocusOnCard - 1, testGameUser.getFocusOnCard());

        assertEquals("Deleting existing number card second time", false, testGameUser.canDeleteCard(testCards.get(2)));
        prevFocusOnCard = testCards.size();
        assertEquals("Deleting existing number card (deleteCard)", prevFocusOnCard - 1, testGameUser.getFocusOnCard());

        assertEquals("Deleting existing special colored card", true, testGameUser.canDeleteCard(testCards.get(3)));
        prevFocusOnCard = testCards.size();
        testGameUser.deleteCard(testCards.get(3));
        assertEquals("Deleting existing special colored card (deleteCard)", prevFocusOnCard - 1, testGameUser.getFocusOnCard());

        assertEquals("Deleting non-existing number card", false, testGameUser.canDeleteCard(new CardResource(16, "yellow", "number", 0, 240, 360, 480, 1080)));
        prevFocusOnCard = testCards.size();
        testGameUser.deleteCard(new CardResource(16, "yellow", "number", 0, 240, 360, 480, 1080));
        assertEquals("Deleting non-existing number card (deleteCard)", prevFocusOnCard, testGameUser.getFocusOnCard());

        assertEquals("Deleting existing color-card", true, testGameUser.canDeleteCard(testCards.get(1)));
        prevFocusOnCard = testCards.size();
        testGameUser.deleteCard(testCards.get(1));
        assertEquals("Deleting existing special color-card (deleteCard)", prevFocusOnCard - 1, testGameUser.getFocusOnCard());

        assertEquals("Deleting existing incFor card", true, testGameUser.canDeleteCard(testCards.get(0)));
        prevFocusOnCard = testCards.size();
        testGameUser.deleteCard(testCards.get(0));
        assertEquals("Deleting existing incFor card (deleteCard)", prevFocusOnCard - 1, testGameUser.getFocusOnCard());
    }

    @Test
    public void testUpdateFocusOnCard() {
        testGameUser.setCards(testCards);
        testGameUser.setFocusOnCard(testCards.size() - 1);

        testGameUser.updateFocusOnCard("right");
        assertEquals("Changing direction to right", testCards.size(), testGameUser.getFocusOnCard());

        testGameUser.updateFocusOnCard("left");
        assertEquals("Changing direction to left", testCards.size() - 1, testGameUser.getFocusOnCard());

        testGameUser.updateFocusOnCard("none");
        assertEquals("No changing direction", testCards.size() - 1, testGameUser.getFocusOnCard());
    }
}
