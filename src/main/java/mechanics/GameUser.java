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
    private GameSession gameSession;
    private int gamePlayerId;

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

    public void setCards(List<CardResource> cards) {
        this.cards = cards;
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
}
