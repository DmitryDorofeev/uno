package mechanics;

import resources.CardResource;
import resources.CardsResource;
import resources.ResourceSystem;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.List;

/**
 * @author alexey
 */
public class GameUser {
    private final String myName;
    private List<CardResource> cards = new ArrayList<>();
    private long playersCount;
    private GameSession gameSession;
    private int gamePlayerId;
    private long focusOnCard;
    private List<CardResource> newCards = new ArrayList<>();

    public GameUser(String myName, long playersCount) {
        this.myName = myName;
        this.playersCount = playersCount;
    }

    public void addCards(List<CardResource> cards) {
        this.cards.addAll(cards);
        saveNewCards(cards);
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
                if (cards.size() == 1)
                    gameSession.setUnoAction();
                return;
            }
        }
    }

    public long getCardsCount() {
        return cards.size();
    }

    public void saveNewCards(List<CardResource> cards) {
        newCards.addAll(cards);
    }

    public List<CardResource> getNewCards() {
        List<CardResource> temp = new ArrayList<>();
        temp.addAll(newCards);
        removeNewCards();
        return temp;
    }

    public void removeNewCards() {
        newCards.clear();
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
        addCards(cards);
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

    public boolean isFocusOnCardValid(long focusOnCard) {
        return focusOnCard >= 0 && focusOnCard < getCardsCount();
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
