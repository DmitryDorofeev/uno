package mechanics;

import resources.CardResource;
import resources.ResourceSystem;

import java.util.*;

/**
 * @author alexey
 */
public class GameSession {
    private Map<String, GameUser> users = new HashMap<>();
    private boolean direction;
    private long curStepPlayerId;
    private CardResource card;
    private Random rnd;

    public GameSession(ArrayList<GameUser> players) {
        for (GameUser player : players)
            users.put(player.getMyName(), player);
        direction = true;
        curStepPlayerId = 0;
        rnd = new Random();
    }

    public CardResource getCard() {
        return card;
    }

    public boolean playerHasCardToSet(GameUser player) {
        List<CardResource> cards = player.getCards();
        for (CardResource card : cards) {
            if (canSetCard(card))
                return true;
        }
        return false;
    }

    public boolean canSetCard(CardResource card) {
        return this.card == null
                || this.card.getColor().equals("black") || card.getColor().equals("black")
                || card.getNum() == this.card.getNum() || card.getColor().equals(this.card.getColor());
    }

    public void setCard(CardResource card) {
        this.card = card;
    }

    public GameUser getUser(String login) {
        return users.get(login);
    }

    public ArrayList<GameUser> getPlayersList() {
        ArrayList<GameUser> result = new ArrayList<>();
        result.addAll(users.values());
        return result;
    }

    public boolean getDirection() {
        return direction;
    }

    public void changeDirection() {
        direction = !direction;
    }

    public long getCurStepPlayerId() {
        return curStepPlayerId;
    }

    public void setCurStepPlayerId(long curStepPlayerId) {
        this.curStepPlayerId = curStepPlayerId;
    }

    public void updateCurStepPlayerId() {
        curStepPlayerId = direction ?
                (curStepPlayerId + 1) % users.size() :
                (curStepPlayerId == 0 ? users.size() - 1 : curStepPlayerId - 1);
    }

    public List<CardResource> generateCards(long count) {
        List<CardResource> cards = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            CardResource temp = ResourceSystem.instance().getCardsResource().getCard(
                    rnd.nextInt(ResourceSystem.instance().getCardsResource().CardsCount()));
            cards.add(new CardResource(temp.getCardId(), temp.getColor(), temp.getNum(),
                    temp.getWidth(), temp.getHeight(), temp.getX(), temp.getY()));
        }
        return cards;
    }
}
