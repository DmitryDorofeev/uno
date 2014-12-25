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

    public void addUser(GameWebSocket user, String extra) {
        if (extra == null)
            userSockets.put(user.getMyName(), user);
        else if (extra.equals("joystick"))
            joystickSockets.put(user.getMyName(), user);
    }

    public void removeUser(GameWebSocket user, String extra) {
        if (extra == null)
            userSockets.remove(user.getMyName());
        else if (extra.equals("joystick"))
            joystickSockets.remove(user.getMyName());
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
        List<CardResource> cards = new ArrayList<>();
        cards.add(user.getGameSession().getCard());
        gameWebSocket.gameStep(correct, message, user.getGameSession().getCurStepPlayerId(),
                cards, user.getGameSession().getDirection(), user.getFocusOnCard(),
                user.getGameSession().getPlayersList());
        if (joystickSockets.containsKey(user.getMyName()))
            sendCardsToJoystick(correct, message, user.getMyName(), user.getFocusOnCard(), user.getCards());
    }

    public void notifyNewCards(boolean correct, String message, GameUser user) {
        GameWebSocket gameWebSocket = userSockets.get(user.getMyName());
        List<CardResource> cards = new ArrayList<>();
        cards.add(user.getGameSession().getCard());
        gameWebSocket.sendNewCards(correct, message, user.getGameSession().getCurStepPlayerId(),
                cards, user.getGameSession().getDirection(), user.getFocusOnCard(),
                user.getGameSession().getPlayersList(), user.getNewCards());
        if (joystickSockets.containsKey(user.getMyName()))
            sendCardsToJoystick(correct, message, user.getMyName(), user.getFocusOnCard(), user.getCards());
    }

    public void notifyChangeFocus(GameUser user) {
        GameWebSocket gameWebSocket = userSockets.get(user.getMyName());
        gameWebSocket.changeFocus(user.getFocusOnCard());
        gameWebSocket = joystickSockets.get(user.getMyName());
        gameWebSocket.changeFocus(user.getFocusOnCard());
    }

    public void notifyUnoFail(String message, GameUser user) {
        GameWebSocket gameWebSocket = userSockets.get(user.getMyName());
        GameUser unoFailPlayer = user.getGameSession().getUnoFailPlayer();
        List<GameUser> players = new ArrayList<>();
        players.add(unoFailPlayer);
        gameWebSocket.sendUnoFail(message, unoFailPlayer.getMyName(), unoFailPlayer.getGamePlayerId(),
                user.getNewCards(), players);
        if (joystickSockets.containsKey(user.getMyName()) && unoFailPlayer.getMyName().equals(user.getMyName())) {
            gameWebSocket = joystickSockets.get(user.getMyName());
            gameWebSocket.sendCardsToJoystick(true, message, user.getFocusOnCard(), user.getCards());
        }
    }

    public void sendCardsToJoystick(boolean correct, String message, String username,
                                     long focusOnCard, List<CardResource> cards) {
        GameWebSocket gameWebSocket = joystickSockets.get(username);
        gameWebSocket.sendCardsToJoystick(correct, message, focusOnCard, cards);
    }
}
