package resources;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexey on 15.11.2014.
 */
public class CardsResource implements Resource {
    private Map<Integer, CardResource> cardResourceMap = new HashMap<>();

    public CardResource getCard(int id) {
        return cardResourceMap.get(id);
    }

    public boolean saveCard(CardResource cardResource) {
        if (!cardResourceMap.containsKey(cardResource.getId())) {
            cardResourceMap.put(cardResource.getId(), cardResource);
            return true;
        }
        return false;
    }

    public int CardsCount() {
        return cardResourceMap.size();
    }
}
