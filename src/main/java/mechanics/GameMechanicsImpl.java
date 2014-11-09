package mechanics;

import base.GameMechanics;
import base.WebSocketService;
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
    }
}
