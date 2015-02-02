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

    long getGameId();

    boolean playerHasCardToSet(GameUser player);

    boolean canSetCard(CardResource card, GameUser player);

    void setCard(CardResource card, String newColor);

    String getColor();

    GameUser getUser(String login);

    ArrayList<GameUser> getPlayersList();

    GameUser getPlayerById(long id);

    GameUser getUnoFailPlayer();

    boolean getDirection();

    boolean actionExists();

    boolean unoActionExists();

    void doAction();

    void setAction(String action);

    void setUnoAction();

    void removeUnoAction(GameUser player, boolean late);

    void changeDirection();

    long getCurStepPlayerId();

    void setCurStepPlayerId(long playerId);

    void updateCurStepPlayerId();

    List<CardResource> generateCards(long count);
}
