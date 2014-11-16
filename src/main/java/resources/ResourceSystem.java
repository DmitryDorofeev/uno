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
        PortResource portResource = (PortResource) ReadXMLFileSAX.readXML("resources/port.xml");
        resourceMap.put("port", portResource);
        DBConfigResource dbConfigResource = (DBConfigResource) ReadXMLFileSAX.readXML("resources/db_config.xml");
        resourceMap.put("db_config", dbConfigResource);
        CardsResource cardsResource = new CardsResource();
        VFS vfs = new VFSImpl("");
        Iterator<String> iter = vfs.getIterator("resources/cards/");
        while (iter.hasNext()) {
            String fileName = iter.next();
            if (!vfs.isDirectory(fileName))
                cardsResource.saveCard((CardResource) ReadXMLFileSAX.readXML(fileName));
        }
        resourceMap.put("cards", cardsResource);
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

    public DBConfigResource getDBConfigResource() {
        return (DBConfigResource)resourceMap.get("db_config");
    }
}
