package mechanics;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author alexey
 */
public class GameSession {
    private Map<String, GameUser> users = new HashMap<>();

    public GameSession(ConcurrentLinkedQueue<GameUser> players) {
        for (GameUser player : players)
            users.put(player.getMyName(), player);
    }

    public GameUser getPlayer(String user) {
        return users.get(user);
    }
}
