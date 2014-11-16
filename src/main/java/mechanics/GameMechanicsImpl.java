package mechanics;

import base.GameMechanics;
import base.WebSocketService;
import resources.CardResource;
import resources.ResourceSystem;
import utils.TimeHelper;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author alexey
 */
public class GameMechanicsImpl implements GameMechanics {
    private WebSocketService webSocketService;

    private ConcurrentLinkedQueue<GameUser> waiters = new ConcurrentLinkedQueue<>();

    public GameMechanicsImpl(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    public void addUser(String user, long playersCount) {
        GameUser newUser = new GameUser(user, playersCount);
        ArrayList<GameUser> tempList = new ArrayList<>();
        tempList.add(newUser);
        Iterator iter = waiters.iterator();
        while (iter.hasNext() && tempList.size() != playersCount) {
            GameUser waiter = (GameUser) iter.next();
            if (waiter.getPlayersCount() == playersCount)
                tempList.add(waiter);
        }
        if (tempList.size() == playersCount) {
            for (GameUser player : tempList)
                waiters.remove(player);
            startGame(tempList);
        } else
            waiters.add(newUser);
        tempList.clear();
    }

    private void startGame(ArrayList<GameUser> players) {
        for (GameUser player : players)
            webSocketService.notifyStartGame(player, players);
        Random rnd = new Random();
        for (GameUser player : players) {
            CardResource[] cards = new CardResource[7];
            for (CardResource card : cards) {
                CardResource temp = ResourceSystem.instance().getCardsResource().getCard(
                        rnd.nextInt(ResourceSystem.instance().getCardsResource().CardsCount()));
                card.setId(temp.getId());
                card.setNum(temp.getNum());
                card.setColor(temp.getColor());
                card.setWidth(temp.getWidth());
                card.setHeight(temp.getHeight());
                card.setX(temp.getX());
                card.setY(temp.getY());
            }
            webSocketService.sendStartCards(player, cards);
        }
    }
}
