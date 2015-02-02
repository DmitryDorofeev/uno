package mechanics;

import resources.CardResource;
import resources.ResourceSystem;

import java.util.*;

/**
 * @author alexey
 */
public class GameSessionImpl implements GameSession {
    private Set<String> actions = new HashSet<>();
    private Map<String, GameUser> users = new HashMap<>();
    private List<Long> unusedCardsId = new ArrayList<>();
    private List<Long> usedCardsId = new ArrayList<>();
    private boolean direction;
    private long curStepPlayerId;
    private long unoPlayerId;
    private CardResource card;
    private String color;
    private Random rnd;
    private String action;
    private boolean uno;
    private long gameId;

    public GameSessionImpl(ArrayList<GameUser> players, long gameId) {
        for (GameUser player : players)
            users.put(player.getMyName(), player);
        direction = true;
        setCurStepPlayerId(0);
        rnd = new Random();
        setGameId(gameId);
        actions.add("incTwo");
        actions.add("incFour");
        actions.add("skip");
        List<Long> cardsIdList = ResourceSystem.instance().getCardsResource().getCardsIdList();
        usedCardsId.addAll(cardsIdList);
        setupUnusedCards();
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public String getAction() {
        return action;
    }

    public boolean actionExists() {
        return getAction() != null;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setUnoAction() {
        uno = true;
        unoPlayerId = getCurStepPlayerId();
    }

    public boolean unoActionExists() {
        return uno;
    }

    public void removeUnoAction(GameUser player, boolean late) {
        if (late)
            player.addCards(generateCards(ResourceSystem.instance().getGameParamsResource().getUnoFailCardsCount()));
        uno = false;
    }

    public void doAction() {
        GameUser player = getPlayerById(getCurStepPlayerId());
        switch (action) {
            case "incTwo":
                player.addCards(generateCards(2));
                break;
            case "skip":
                break;
            case "incFour":
                player.addCards(generateCards(4));
                break;
        }
        removeAction();
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
        return isCorrectNotIncFourCard(card) || playerCanSetIncFourCard(player);
    }

    public void setCard(CardResource card, String newColor) {
        if (this.card != null)
            usedCardsId.add(this.card.getCardId());
        this.card = card;
        setColor(card.getColor().equals("black") ? newColor : card.getColor());
        if (card.getType().equals("reverse"))
            changeDirection();
        if (actions.contains(card.getType()))
            setAction(card.getType());
    }

    public void setColor(String newColor) {
        this.color = newColor;
    }

    public String getColor() {
        return color;
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

    public void setCurStepPlayerId(long playerId) {
        this.curStepPlayerId = playerId;
    }

    public void updateCurStepPlayerId() {
        setCurStepPlayerId(getNextStepPlayerId());
    }

    public GameUser getPlayerById(long id) {
        GameUser foundPlayer = null;
        List<GameUser> players = getPlayersList();
        for (GameUser player : players) {
            if (player.getGamePlayerId() == id)
                foundPlayer = player;
        }
        return foundPlayer;
    }

    public GameUser getUnoFailPlayer() {
        return getPlayerById((int)unoPlayerId);
    }

    private long getNextStepPlayerId() {
        return direction ?
                (curStepPlayerId + 1) % users.size() :
                (curStepPlayerId == 0 ? users.size() - 1 : curStepPlayerId - 1);
    }

    public List<CardResource> generateCards(long count) {
        List<CardResource> cards = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            if (unusedCardsId.size() == 0) {
                setupUnusedCards();
            }
            int index = rnd.nextInt(unusedCardsId.size());
            CardResource temp = ResourceSystem.instance().getCardsResource().getCard(unusedCardsId.get(index));
            unusedCardsId.remove(index);

            cards.add(new CardResource(temp.getCardId(), temp.getColor(), temp.getType(), temp.getNum(),
                    temp.getWidth(), temp.getHeight(), temp.getX(), temp.getY()));
        }
        return cards;
    }

    private void removeAction() {
        setAction(null);
    }

    private boolean isCorrectNotIncFourCard(CardResource card) {
        return !card.getType().equals("incFour") && (
                card.getType().equals("number") && this.card.getType().equals("number") && card.getNum() == this.card.getNum()
                || card.getColor().equals(color)
                || !card.getType().equals("number") && card.getType().equals(getCardType())
                || card.getType().equals("color")
        );
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
        return !card.getColor().equals(color);
    }

    private boolean playerCanSetIncFourCard(GameUser player) {
        List<CardResource> cards = player.getCards();
        boolean incFourFound = false;
        for (CardResource card : cards) {
            if (!isCorrectForIncFourCard(card))
                return false;
            if (card.getType().equals("incFour"))
                incFourFound = true;
        }
        return incFourFound;
    }

    private void setupUnusedCards() {
        unusedCardsId.addAll(usedCardsId);
        usedCardsId.clear();
    }
}
