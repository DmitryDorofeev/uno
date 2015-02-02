package resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alexey on 15.11.2014.
 */
public class CardsResource implements Resource {
    private Map<Long, CardResource> cardResourceMap = new HashMap<>();

    public CardResource getCard(long id) {
        return cardResourceMap.get(id);
    }

    public boolean saveCard(CardResource cardResource) {
        if (!cardResourceMap.containsKey(cardResource.getCardId())) {
            cardResourceMap.put(cardResource.getCardId(), cardResource);
            return true;
        }
        return false;
    }

    public int CardsCount() {
        return cardResourceMap.size();
    }

    public List<Long> getCardsIdList() {
        List<Long> result = new ArrayList<>();
        for (Long key : cardResourceMap.keySet()) {
            result.add(key);
        }
        return result;
    }
}
