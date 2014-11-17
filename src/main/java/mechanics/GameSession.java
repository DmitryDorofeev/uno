package mechanics;

import resources.CardResource;

import java.util.ArrayList;

/**
 * @author alexey
 */
public class GameSession {
    private ArrayList<GameUser> users = new ArrayList<GameUser>();
    private boolean direction;
    private long curStepPlayerId;
    private CardResource card;

    public GameSession(ArrayList<GameUser> players) {
        for (GameUser player : players)
            users.add(player);
        direction = true;
        curStepPlayerId = 0;
    }

    public CardResource getCard() {
        return card;
    }

    public void setCard(CardResource card) {
        this.card = card;
    }

    public ArrayList<GameUser> getPlayersList() {
        return users;
    }

    public GameUser getPlayerById(Integer id) {
        return users.get(id);
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
}
