package frontend;

import mechanics.GameUser;
import base.WebSocketService;
import resources.CardResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author alexey
 */
public class WebSocketServiceImpl implements WebSocketService {
    private Map<String, GameWebSocket> userSockets = new HashMap<>();
    private Map<String, GameWebSocket> joystickSockets = new HashMap<>();

    public void addUser(GameWebSocket user) {
        if (!userSockets.containsKey(user.getMyName()))
            userSockets.put(user.getMyName(), user);
        else if (!joystickSockets.containsKey(user.getMyName()))
            joystickSockets.put(user.getMyName(), user);
    }

    public boolean checkUser(String name) {
        return userSockets.containsKey(name);
    }

    public void removeUser(GameWebSocket user) {
        userSockets.remove(user.getMyName());
    }

    public void notifyStartGame(GameUser user) {
        GameWebSocket gameWebSocket = userSockets.get(user.getMyName());
        gameWebSocket.startGame(user.getGameSession().getPlayersList());
    }

    public void sendStartCards(GameUser user) {
        GameWebSocket gameWebSocket = userSockets.get(user.getMyName());
        gameWebSocket.sendStartCards(user.getCards());
    }

    public void notifyGameStep(boolean correct, String message, GameUser user) {
        GameWebSocket gameWebSocket = userSockets.get(user.getMyName());
        gameWebSocket.gameStep(correct, message, user.getGameSession().getCurStepPlayerId(),
                user.getGameSession().getCard(), user.getGameSession().getDirection());
    }

    public void initJoystick(String message, String username, List<CardResource> cards) {
        GameWebSocket gameWebSocket = joystickSockets.get(username);
        gameWebSocket.initJoystick(message, cards);
    }
}
