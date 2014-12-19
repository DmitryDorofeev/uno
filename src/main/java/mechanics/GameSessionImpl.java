package mechanics;

import resources.CardResource;
import resources.ResourceSystem;

import java.util.*;

/**
 * @author alexey
 */
public class GameSessionImpl implements GameSession {
    private Map<String, GameUser> users = new HashMap<>();
    private boolean direction;
    private long curStepPlayerId;
    private CardResource card;
    private String color;
    private Random rnd;

    public GameSessionImpl(ArrayList<GameUser> players) {
        for (GameUser player : players)
            users.put(player.getMyName(), player);
        direction = true;
        setCurStepPlayerId(0);
        rnd = new Random();
    }

    public String getCardType() {
        return card.getType();
    }

    public CardResource getCard() {
        return card;
    }

    public boolean playerHasCardToSet(GameUser player) {
        return playerHasCardNotIncFourToSet(player) || playerCanSetIncFourCard(player);
    }

    public boolean canSetCard(CardResource card, GameUser player) {
        return this.card == null
                || isCorrectNotIncFourCard(card)
                || playerCanSetIncFourCard(player) && card.getType().equals("incFour");
    }

    public void setCard(CardResource card, String newColor) {
        this.card = card;
        this.color = card.getColor().equals("black") ? newColor : card.getColor();
        if (card.getType().equals("reverse"))
            changeDirection();
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
        if (!card.getType().equals("reverse"))
            curStepPlayerId = direction ?
                (curStepPlayerId + 1) % users.size() :
                (curStepPlayerId == 0 ? users.size() - 1 : curStepPlayerId - 1);
    }

    public List<CardResource> generateCards(long count) {
        List<CardResource> cards = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            CardResource temp = ResourceSystem.instance().getCardsResource().getCard(
                    rnd.nextInt(ResourceSystem.instance().getCardsResource().CardsCount()));
            cards.add(new CardResource(temp.getCardId(), temp.getColor(), temp.getType(), temp.getNum(),
                    temp.getWidth(), temp.getHeight(), temp.getX(), temp.getY()));
        }
        return cards;
    }

    private boolean isCorrectNotIncFourCard(CardResource card) {
        return card.getType().equals("number") && this.card.getType().equals("number") && card.getNum() == this.card.getNum()
                || card.getColor().equals(color)
                || card.getType().equals("color");
    }

    private boolean playerHasCardNotIncFourToSet(GameUser player) {
        List<CardResource> cards = player.getCards();
        for (CardResource card : cards) {
            if (isCorrectNotIncFourCard(card))
                return true;
        }
        return false;
    }

    private boolean isCorrectForIncFourCard(CardResource card) {
        return !(card.getColor().equals(color) || card.getType().equals("color"));
    }

    private boolean playerCanSetIncFourCard(GameUser player) {
        List<CardResource> cards = player.getCards();
        boolean incFourFound = false;
        for (CardResource card : cards) {
            if (!isCorrectForIncFourCard(card))
                return false;
            if (card.getType().equals("incFor"))
                incFourFound = true;
        }
        return incFourFound;
    }
}
