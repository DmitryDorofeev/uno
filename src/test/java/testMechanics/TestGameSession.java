package testMechanics;

import static org.mockito.Mockito.*;

import db.DBServiceImpl;
import mechanics.GameSession;
import mechanics.GameSessionImpl;
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
public class TestGameSession {
    GameSession testGameSession = new GameSessionImpl(new ArrayList<GameUser>(), (long) 1); // НУЖЕН ФИКС, КОСТЯН

    public List<CardResource> makeCardList(int conditions) {
        List<CardResource> cards = new ArrayList<>();
        switch (conditions) {
            case 1:
                cards.add(new CardResource(5, "red", "number", 1, 240, 360, 240, 0));
                cards.add(new CardResource(3, "green", "number", 0, 240, 360, 0, 720));
                cards.add(new CardResource(15, "green", "number", 2, 240, 360, 480, 720));
                break;
            case 2:
                cards.add(new CardResource(2, "yellow", "number", 0, 240, 360, 0, 360));
                cards.add(new CardResource(3, "green", "number", 0, 240, 360, 0, 720));
                cards.add(new CardResource(15, "green", "number", 2, 240, 360, 480, 720));
                break;
            case 3:
                cards.add(new CardResource(101, "black", "color", 13, 240, 360, 2880, 0));
                cards.add(new CardResource(3, "green", "number", 0, 240, 360, 0, 720));
                cards.add(new CardResource(15, "green", "number", 2, 240, 360, 480, 720));
                break;
            case 4:
                cards.add(new CardResource(105, "black", "incFour", 14, 240, 360, 2880, 1440));
                cards.add(new CardResource(101, "black", "color", 13, 240, 360, 2880, 0));
                cards.add(new CardResource(15, "green", "number", 2, 240, 360, 480, 720));
                break;
            case 5:
                cards.add(new CardResource(105, "black", "incFour", 14, 240, 360, 2880, 1440));
                cards.add(new CardResource(3, "green", "number", 0, 240, 360, 0, 720));
                cards.add(new CardResource(15, "green", "number", 2, 240, 360, 480, 720));
                break;
            case 6:
                cards.add(new CardResource(105, "black", "incFour", 14, 240, 360, 2880, 1440));
                cards.add(new CardResource(3, "green", "number", 0, 240, 360, 0, 720));
                cards.add(new CardResource(15, "green", "number", 2, 240, 360, 480, 720));
                cards.add(new CardResource(2, "yellow", "number", 0, 240, 360, 0, 360));
            case 7:
                cards.add(new CardResource(3, "green", "number", 0, 240, 360, 0, 720));
                cards.add(new CardResource(15, "green", "number", 2, 240, 360, 480, 720));
                break;
            case 8:
                cards.add(new CardResource(105, "black", "incFour", 14, 240, 360, 2880, 1440));
                cards.add(new CardResource(101, "black", "color", 13, 240, 360, 2880, 0));
                cards.add(new CardResource(2, "yellow", "number", 0, 240, 360, 0, 360));
                cards.add(new CardResource(3, "green", "reverse", 11, 240, 360, 2400, 720));
        }
        return cards;
    }

    @Test
    public void testPlayerHasCardToSet () {
        GameUser testPlayer = mock(GameUser.class);
        List<CardResource> testCards = new ArrayList<>();

        for (int i = 1; i < 8; ++i) {
            testCards = makeCardList(i);
            when(testPlayer.getCards()).thenReturn(testCards);
            testGameSession.setCard(new CardResource(6, "yellow", "number", 1, 240, 360, 240, 360), "yellow");
            if (i != 7) {
                assertEquals("Player has cards according to conditions " + i, true, testGameSession.playerHasCardToSet(testPlayer));
            }
            else {
                assertEquals("Player has cards according to conditions " + i, false, testGameSession.playerHasCardToSet(testPlayer));
            }
        }

    }

    @Test
    public void testCanSetCard () {
        GameUser testPlayer = mock(GameUser.class);
        List<CardResource> testCards = new ArrayList<>();

        assertEquals("Setting card when no card", true, testGameSession.canSetCard(new CardResource(6, "yellow", "number", 1, 240, 360, 240, 360), testPlayer));

        testGameSession.setCard(new CardResource(6, "yellow", "number", 1, 240, 360, 240, 360), "yellow");

        testCards = makeCardList(2);
        when(testPlayer.getCards()).thenReturn(testCards);
        assertEquals("Setting correct notIncFour card", true, testGameSession.canSetCard(testCards.get(0), testPlayer));

        testCards = makeCardList(5);
        when(testPlayer.getCards()).thenReturn(testCards);
        assertEquals("Setting IncFour card", true, testGameSession.canSetCard(testCards.get(0), testPlayer));

        testCards = makeCardList(7);
        when(testPlayer.getCards()).thenReturn(testCards);
        assertEquals("Setting incorrect card", false, testGameSession.canSetCard(testCards.get(0), testPlayer));
    }

    @Test
    public void testSetCard () {
        List<CardResource> testCards = makeCardList(8);

        testGameSession.setCard(testCards.get(2), "blue");
        assertEquals("(setCard) Setting number yellow card, checking type", "number", testGameSession.getCardType());
        assertEquals("(setCard) Setting number yellow card, checking color", "yellow", testGameSession.getColor());

        testGameSession.setCard(testCards.get(1), "blue");
        assertEquals("(setCard) Setting color card, checking type", "color", testGameSession.getCardType());
        assertEquals("(setCard) Setting color card, checking color", "blue", testGameSession.getColor());
        testGameSession.setCard(testCards.get(1), null);
        assertEquals("(setCard) Setting color card, checking type", "color", testGameSession.getCardType());
        assertEquals("(setCard) Setting color card, checking color", isNull(), testGameSession.getColor());

        testGameSession.setCard(testCards.get(0), "red");
        assertEquals("(setCard) Setting incFour card, checking type", "incFour", testGameSession.getCardType());
        assertEquals("(setCard) Setting incFour card, checking color", "red", testGameSession.getColor());
        testGameSession.setCard(testCards.get(0), null);
        assertEquals("(setCard) Setting incFour card, checking type", "incFour", testGameSession.getCardType());
        assertEquals("(setCard) Setting incFour card, checking color", isNull(), testGameSession.getColor());

        boolean prevDirection = testGameSession.getDirection();
        testGameSession.setCard(testCards.get(3), "blue");
        assertEquals("(setCard) Setting reverse card, checking type", "reverse", testGameSession.getCardType());
        assertEquals("(setCard) Setting reverse card, checking color", "green", testGameSession.getColor());
        assertEquals("(setCard) Setting reverse card, checking direction", !prevDirection, testGameSession.getDirection());
    }
}
