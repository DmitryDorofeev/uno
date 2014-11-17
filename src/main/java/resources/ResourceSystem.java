package resources;

import sax.ReadXMLFileSAX;
import vfs.VFS;
import vfs.VFSImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by alexey on 15.11.2014.
 */
public class ResourceSystem {
    private static ResourceSystem resourceSystem;
    private Map<String, Resource> resourceMap = new HashMap<>();

    protected ResourceSystem() {
        System.out.println("Resources loading started");

        PortResource portResource;
        if (VFS.exists("resources/port.xml"))
            portResource = (PortResource) ReadXMLFileSAX.readXML("resources/port.xml");
        else {
            System.out.println("File resources/port.xml does not exist");
            portResource = new PortResource();
        }
        resourceMap.put("port", portResource);

        DBConfigResource dbConfigResource;
        if (VFS.exists("resources/db_config.xml"))
            dbConfigResource = (DBConfigResource) ReadXMLFileSAX.readXML("resources/db_config.xml");
        else {
            System.out.println("File resources/db_config.xml does not exist");
            dbConfigResource = new DBConfigResource();
        }
        resourceMap.put("db_config", dbConfigResource);

        GameParamsResource gameParamsResource;
        if (VFS.exists("resources/game.xml"))
            gameParamsResource = (GameParamsResource) ReadXMLFileSAX.readXML("resources/game.xml");
        else {
            System.out.println("File resources/game.xml does not exist");
            gameParamsResource = new GameParamsResource();
        }
        resourceMap.put("game", gameParamsResource);

        CardsResource cardsResource = new CardsResource();
        VFS vfs = new VFSImpl("");
        Iterator<String> iter = vfs.getIterator("resources/cards/");
        while (iter.hasNext()) {
            String fileName = iter.next();
            if (VFS.exists(fileName)) {
                if (!VFS.isDirectory(fileName))
                    cardsResource.saveCard((CardResource) ReadXMLFileSAX.readXML(fileName));
            }
            else {
                System.out.println("File " + fileName + " does not exist");
                cardsResource.saveCard(new CardResource());
            }
        }
        resourceMap.put("cards", cardsResource);

        System.out.println("Resources loading finished");
    }

    public static ResourceSystem instance() {
        if (resourceSystem == null)
            resourceSystem = new ResourceSystem();
        return resourceSystem;
    }

    public PortResource getPortResource() {
        return (PortResource)resourceMap.get("port");
    }

    public CardsResource getCardsResource() {
        return (CardsResource)resourceMap.get("cards");
    }

    public GameParamsResource getGameParamsResource() {
        return (GameParamsResource)resourceMap.get("game");
    }

    public DBConfigResource getDBConfigResource() {
        return (DBConfigResource)resourceMap.get("db_config");
    }
}
