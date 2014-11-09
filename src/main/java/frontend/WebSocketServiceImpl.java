package frontend;

import mechanics.GameUser;
import base.WebSocketService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author alexey
 */
public class WebSocketServiceImpl implements WebSocketService {
    private Map<String, GameWebSocket> userSockets = new HashMap<>();

    public void addUser(GameWebSocket user) {
        userSockets.put(user.getMyName(), user);
    }

    public void notifyStartGame(GameUser user, ArrayList<GameUser> players) {
        GameWebSocket gameWebSocket = userSockets.get(user.getMyName());
        gameWebSocket.startGame(players);
    }
}
