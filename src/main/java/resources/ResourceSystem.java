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

        ServerConfigResource serverConfigResource;
        if (VFS.exists("resources/server_config.xml"))
            serverConfigResource = (ServerConfigResource) ReadXMLFileSAX.readXML("resources/server_config.xml");
        else {
            System.out.println("File resources/server_config.xml does not exist");
            serverConfigResource = new ServerConfigResource();
        }
        resourceMap.put("resources/server_config.xml", serverConfigResource);

        DBConfigResource dbConfigResource;
        if (VFS.exists("resources/db_config.xml"))
            dbConfigResource = (DBConfigResource) ReadXMLFileSAX.readXML("resources/db_config.xml");
        else {
            System.out.println("File resources/db_config.xml does not exist");
            dbConfigResource = new DBConfigResource();
        }
        resourceMap.put("resources/db_config.xml", dbConfigResource);

        GameParamsResource gameParamsResource;
        if (VFS.exists("resources/game.xml"))
            gameParamsResource = (GameParamsResource) ReadXMLFileSAX.readXML("resources/game.xml");
        else {
            System.out.println("File resources/game.xml does not exist");
            gameParamsResource = new GameParamsResource();
        }
        resourceMap.put("resources/game.xml", gameParamsResource);

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
        resourceMap.put("resources/cards/", cardsResource);

        System.out.println("Resources loading finished");
    }

    public static ResourceSystem instance() {
        if (resourceSystem == null)
            resourceSystem = new ResourceSystem();
        return resourceSystem;
    }

    public ServerConfigResource getServerStartResource() {
        return (ServerConfigResource)resourceMap.get("resources/server_config.xml");
    }

    public CardsResource getCardsResource() {
        return (CardsResource)resourceMap.get("resources/cards/");
    }

    public GameParamsResource getGameParamsResource() {
        return (GameParamsResource)resourceMap.get("resources/game.xml");
    }

    public DBConfigResource getDBConfigResource() {
        return (DBConfigResource)resourceMap.get("resources/db_config.xml");
    }
}
