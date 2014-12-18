package mechanics;

import resources.CardResource;
import resources.ResourceSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alexey
 */
public class GameUser {
    private final String myName;
    private List<CardResource> cards;
    private long playersCount;
    private GameSession gameSession;
    private int gamePlayerId;
    private long focusOnCard;

    public GameUser(String myName, long playersCount) {
        this.myName = myName;
        this.playersCount = playersCount;
    }

    public boolean canDeleteCard(CardResource card) {
        for (CardResource curCard : cards) {
            if (curCard.getCardId() == card.getCardId())
                return true;
        }
        return false;
    }

    public void deleteCard(CardResource card) {
        for (CardResource curCard : cards) {
            if (curCard.getCardId() == card.getCardId()) {
                cards.remove(curCard);
                if (cards.size() == focusOnCard)
                    focusOnCard--;
                return;
            }
        }
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

    @Deprecated
    public List<CardResource> getCardsForJoystick() {
        int last = (int)focusOnCard + ResourceSystem.instance().getGameParamsResource().getJoystickCardsCount();
        return cards.subList((int)focusOnCard, last);
    }

    public void setCards(List<CardResource> cards) {
        this.cards = cards;
        setFocusOnCard(0);
    }

    public GameSession getGameSession() {
        return gameSession;
    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    public int getGamePlayerId() {
        return gamePlayerId;
    }

    public void setGamePlayerId(int gamePlayerId) {
        this.gamePlayerId = gamePlayerId;
    }

    public long getFocusOnCard() {
        return focusOnCard;
    }

    public void setFocusOnCard(long focusOnCard) {
        this.focusOnCard = focusOnCard;
    }

    public long getFocusedCardId() {
        return cards.get((int)focusOnCard).getCardId();
    }

    public void updateFocusOnCard(String direction) {
        if (direction.equals("right"))
            focusOnCard = (focusOnCard + 1) % cards.size();
        else if (direction.equals("left"))
            focusOnCard = (focusOnCard == 0) ? cards.size() - 1 : focusOnCard - 1;
    }
}
