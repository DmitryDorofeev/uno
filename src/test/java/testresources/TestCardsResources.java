package testresources;

import org.junit.*;
import static org.mockito.Mockito.*;

import resources.CardsResource;
import resources.CardResource;
import sax.ReadXMLFileSAX;
import vfs.VFS;
import vfs.VFSImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by К on 11.12.2014.
 */
public class TestCardsResources {
    public CardsResource testCardsResource = new CardsResource();
    public static List<CardResource> testCards = new ArrayList<>();
    public String colors[] = { "red", "yellow", "green", "blue" };

    @BeforeClass
    public static void initTestValues() {
        testCards.add(new CardResource(109, "red", "number", 0, 240, 360, 0, 0));
        testCards.add(new CardResource(114, "red", "number", 1, 240, 360, 240, 0));
    }

    @Test
    public void testSavingCards() {
        for (int i = 1; i < 5; ++i) {
            testCards.remove(0);
            testCards.add(0, new CardResource(i, colors[i%4], "number", 0, 240, 360, 0, 360*(i-1)));
            assertEquals("Adding card first time" + i, true, testCardsResource.saveCard(testCards.get(0)));
        }

        int number = 0;
        int x = 0;

        for (int i = 5; i < 77; ++i) {
            if (((i - 5)%8) == 0) {
                number++;
                x += 240;
            }
            testCards.remove(0);
            testCards.add(0, new CardResource(i, colors[i%4], "number", number, 240, 360, x, 360*((i-5)%8)));
            assertEquals("Adding card first time " + i, true, testCardsResource.saveCard(testCards.get(0)));
        }

        for (int i = 77; i < 85; ++i) {
            if (((i - 5)%8) == 0) {
                number++;
                x += 240;
            }
            testCards.remove(0);
            testCards.add(0, new CardResource(i, colors[i%4], "skip", number, 240, 360, x, 360*((i-5)%8)));
            assertEquals("Adding card first time " + i, true, testCardsResource.saveCard(testCards.get(0)));
        }

        for (int i = 85; i < 93; ++i) {
            if (((i - 5)%8) == 0) {
                number++;
                x += 240;
            }
            testCards.remove(0);
            testCards.add(0, new CardResource(i, colors[i%4], "reverse", number, 240, 360, x, 360*((i-5)%8)));
            assertEquals("Adding card first time " + i, true, testCardsResource.saveCard(testCards.get(0)));
        }

        for (int i = 93; i < 101; ++i) {
            if (((i - 5)%8) == 0) {
                number++;
                x += 240;
            }
            testCards.remove(0);
            testCards.add(0, new CardResource(i, colors[i%4], "incTwo", number, 240, 360, x, 360*((i-5)%8)));
            assertEquals("Adding card first time " + i, true, testCardsResource.saveCard(testCards.get(0)));
        }

        for (int i = 101; i < 105; ++i) {
            testCards.remove(0);
            testCards.add(0, new CardResource(i, "black", "color", 13, 240, 360, 0, 360*((i-101)%8)));
            assertEquals("Adding card first time" + i, true, testCardsResource.saveCard(testCards.get(0)));
        }

        for (int i = 105; i < 109; ++i) {
            testCards.remove(0);
            testCards.add(0, new CardResource(i, "black", "incFour", 14, 240, 360, 0, 360*((i-105)%8)));
            assertEquals("Adding card first time" + i, true, testCardsResource.saveCard(testCards.get(0)));
        }
        assertEquals("Cards' count is " + testCardsResource.CardsCount(), 108, testCardsResource.CardsCount());

//        assertEquals("Adding card second time with new ID " + testCards.get(1).getCardId(), false, testCardsResource.saveCard(testCards.get(1)));
//        assertEquals("Cards' count is " + testCardsResource.CardsCount(), 108, testCardsResource.CardsCount());

        testCards.get(1).setCardId(1);
        assertEquals("Adding card second time with the same ID " + testCards.get(1).getCardId(), false, testCardsResource.saveCard(testCards.get(1)));
        assertEquals("Cards' count is " + testCardsResource.CardsCount(), 108, testCardsResource.CardsCount());

//        assertEquals("Adding card second time with the new ID " + testCards.get(2).getCardId(), false, testCardsResource.saveCard(testCards.get(2)));
//        assertEquals("Cards' count is " + testCardsResource.CardsCount(), 108, testCardsResource.CardsCount());
    }

    @Test
    public void testParsingCards() throws Exception {
        HashMap<String, String> colorMap = new HashMap<>();
        HashMap<String, String> typeMap = new HashMap<>();

        colorMap.put("red", "1");
        colorMap.put("yellow", "2");
        colorMap.put("green", "3");
        colorMap.put("blue", "4");
        colorMap.put("black", "5");

        typeMap.put("number", "1");
        typeMap.put("skip", "2");
        typeMap.put("reverse", "3");
        typeMap.put("incTwo", "4");
        typeMap.put("color", "5");
        typeMap.put("incFour", "6");

        CardsResource cardsResource = new CardsResource();
        VFS vfs = new VFSImpl("");
        Iterator<String> iter = vfs.getIterator("resources/cards/");
        while (iter.hasNext()) {
            String fileName = iter.next();
            if (VFS.exists(fileName)) {
                if (!VFS.isDirectory(fileName)) {
                    CardResource tempCard = (CardResource) ReadXMLFileSAX.readXML(fileName);
                    cardsResource.saveCard(tempCard);
                    assertEquals("Checking colour", true, colorMap.containsKey(tempCard.getColor()));
                    assertEquals("Checking type", true, typeMap.containsKey(tempCard.getType()));
                    if (tempCard.getType().equals("number"))
                        assertEquals("Checking number", true, ((tempCard.getNum() >= 0) && (tempCard.getNum() <= 9)));
                }
            }
            else {
                System.out.println("File " + fileName + " does not exist");
                cardsResource.saveCard(new CardResource());
            }
        }
    }
}
