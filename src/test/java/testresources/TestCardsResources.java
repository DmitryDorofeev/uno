package testresources;

import org.junit.*;
import resources.CardsResource;
import resources.CardResource;

import static org.junit.Assert.*;

/**
 * Created by К on 11.12.2014.
 */
public class TestCardsResources {
    public CardsResource testCardsResource = new CardsResource();
    public static CardResource testCards[] = new CardResource[3];
    public String colors[] = { "red", "yellow", "green", "blue" };

    @BeforeClass
    public static void initTestValues() {
        testCards[0] = new CardResource();
        testCards[1] = new CardResource(109, "red", 0, 240, 360, 0, 0);
        testCards[2] = new CardResource(114, "red", 1, 240, 360, 240, 0);
    }

    @Test
    public void testSavingCards() {
        initTestValues();
        for (int i = 1; i < 5; ++i) {
            testCards[0].setAllParams(i, colors[i%4], 0, 240, 360, 0, 360*(i-1));
            assertEquals("Adding card first time" + i, true, testCardsResource.saveCard(testCards[0]));
        }

        int number = 0;
        int x = 0;

        for (int i = 5; i < 101; ++i) {
            if (((i - 5)%8) == 0) {
                number++;
                x += 240;
            }
            testCards[0].setAllParams(i, colors[i%4], number, 240, 360, x, 360*((i-5)%8));
            assertEquals("Adding card first time " + i, true, testCardsResource.saveCard(testCards[0]));
        }

        for (int i = 101; i < 105; ++i) {
            testCards[0].setAllParams(i, "black", 13, 240, 360, 0, 360*((i-101)%8));
            assertEquals("Adding card first time" + i, true, testCardsResource.saveCard(testCards[0]));
        }

        for (int i = 105; i < 109; ++i) {
            testCards[0].setAllParams(i, "black", 14, 240, 360, 0, 360*((i-105)%8));
            assertEquals("Adding card first time" + i, true, testCardsResource.saveCard(testCards[0]));
        }
        assertEquals("Cards' count is " + testCardsResource.CardsCount(), 108, testCardsResource.CardsCount());

        assertEquals("Adding card second time with new ID " + testCards[1].getCardId(), false, testCardsResource.saveCard(testCards[1]));
        assertEquals("Cards' count is " + testCardsResource.CardsCount(), 108, testCardsResource.CardsCount());

        testCards[1].setCardId(1);
        assertEquals("Adding card second time with the same ID " + testCards[1].getCardId(), false, testCardsResource.saveCard(testCards[1]));
        assertEquals("Cards' count is " + testCardsResource.CardsCount(), 108, testCardsResource.CardsCount());

        assertEquals("Adding card second time with the new ID " + testCards[2].getCardId(), false, testCardsResource.saveCard(testCards[2]));
        assertEquals("Cards' count is " + testCardsResource.CardsCount(), 108, testCardsResource.CardsCount());
    }
}
