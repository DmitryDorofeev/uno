package mechanics;

import resources.CardResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexey on 19.12.2014.
 */
public interface GameSession {
    String getCardType();

    CardResource getCard();

    boolean playerHasCardToSet(GameUser player);

    boolean canSetCard(CardResource card, GameUser player);

    void setCard(CardResource card, String newColor);

    GameUser getUser(String login);

    ArrayList<GameUser> getPlayersList();

    boolean getDirection();

    void changeDirection();

    long getCurStepPlayerId();

    void setCurStepPlayerId(long curStepPlayerId);

    void updateCurStepPlayerId();

    List<CardResource> generateCards(long count);
}
