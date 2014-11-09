package base;

import frontend.GameWebSocket;
import mechanics.GameUser;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author alexey
 */
public interface WebSocketService {

    void addUser(GameWebSocket user);

    void notifyStartGame(GameUser user, ArrayList<GameUser> players);
}
