package mechanics;

import resources.CardResource;

import java.util.List;

/**
 * @author alexey
 */
public class GameUser {
    private final String myName;
    private List<CardResource> cards;
    private long playersCount;

    public GameUser(String myName, long playersCount) {
        this.myName = myName;
        this.playersCount = playersCount;
    }

    public String getMyName() {
        return myName;
    }

    public long getPlayersCount() {
        return playersCount;
    }

    public List<CardResource> getCards() {
        return cards;
    }

    public void setCards(List<CardResource> cards) {
        this.cards = cards;
    }
}
