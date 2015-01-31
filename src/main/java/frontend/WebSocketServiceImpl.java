package frontend;

import MessageSystem.Msg;
import MessageSystem.Address;
import MessageSystem.MessageSystem;
import mechanics.GameUser;
import base.WebSocketService;
import org.json.simple.JSONObject;
import resources.CardResource;
import resources.ResourceSystem;
import utils.LoggerHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author alexey
 */
public class WebSocketServiceImpl implements WebSocketService, Runnable {
    private Address address = new Address();
    private Map<String, GameWebSocket> userSockets = new HashMap<>();
    private Map<String, GameWebSocket> joystickSockets = new HashMap<>();
    final private Object object1 = new Object();
    final private Object object2 = new Object();

    public WebSocketServiceImpl() {
        MessageSystem.instance().addService(this);
        MessageSystem.instance().getAddressService().setWebSocketService(getAddress());
    }

    public synchronized void addUser(GameWebSocket user, String extra) {
        if (extra == null)
            userSockets.put(user.getMyName(), user);
        else if (extra.equals("joystick"))
            joystickSockets.put(user.getMyName(), user);
    }

    public synchronized void removeUser(GameWebSocket user, String extra) {
        if (extra == null)
            userSockets.remove(user.getMyName());
        else if (extra.equals("joystick"))
            joystickSockets.remove(user.getMyName());
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void run() {
        while (true) {
            try {
                MessageSystem.instance().execForAbonent(this);
                Thread.sleep(ResourceSystem.instance().getServerConfigResource().getServiceSleepTime());
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyStartGame(GameUser user) {
        GameWebSocket gameWebSocket = userSockets.get(user.getMyName());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "info");
        JSONObject jsonBody = new JSONObject();
        jsonObject.put("body", jsonBody);
        jsonBody.put("gameWebSocket", gameWebSocket != null ? "is not null" : "is null");
        jsonBody.put("user", user != null ? "is not null" : "is null");
        jsonBody.put("user.gameSession", user.getGameSession() != null ? "is not null" : "is null");
        LoggerHelper.logJSON("notifyStartGame", jsonObject);
        gameWebSocket.startGame(user.getGameSession().getPlayersList());
    }

    public void sendCards(GameUser user) {
        GameWebSocket gameWebSocket = userSockets.get(user.getMyName());
        gameWebSocket.sendCards(user.getNewCards());
    }

    public void notifyGameStep(boolean correct, String message, GameUser user, String fromJoystick) {
        synchronized (object1) {
            GameWebSocket gameWebSocket = userSockets.get(user.getMyName());
            List<CardResource> cards = new ArrayList<>();
            CardResource card = user.getGameSession().getCard();
            card.setColor(user.getGameSession().getColor());
            cards.add(card);
            gameWebSocket.gameStep(correct, message, user.getGameSession().getCurStepPlayerId(),
                    cards, user.getGameSession().getDirection(), user.getFocusOnCard(),
                    user.getGameSession().getPlayersList(), fromJoystick);
            if (joystickSockets.containsKey(user.getMyName()))
                sendCardsToJoystick(correct, message, user.getMyName(), user.getFocusOnCard(), user.getCards());
        }
    }

    public void notifyChangeFocus(GameUser user) {
        GameWebSocket gameWebSocket = userSockets.get(user.getMyName());
        gameWebSocket.changeFocus(user.getFocusOnCard());
        gameWebSocket = joystickSockets.get(user.getMyName());
        gameWebSocket.changeFocus(user.getFocusOnCard());
    }

    public void notifyUnoFail(String message, GameUser user) {
        synchronized (object2) {
            GameWebSocket gameWebSocket = userSockets.get(user.getMyName());
            GameUser unoFailPlayer = user.getGameSession().getUnoFailPlayer();
            List<GameUser> players = new ArrayList<>();
            players.add(unoFailPlayer);
            gameWebSocket.sendUnoFail(message, unoFailPlayer.getGamePlayerId(), players);
            if (joystickSockets.containsKey(user.getMyName()) && unoFailPlayer.getMyName().equals(user.getMyName())) {
                gameWebSocket = joystickSockets.get(user.getMyName());
                gameWebSocket.sendCardsToJoystick(true, message, user.getFocusOnCard(), user.getCards());
            }
        }
    }

    public void sendCardsToJoystick(boolean correct, String message, String username,
                                     long focusOnCard, List<CardResource> cards) {
        GameWebSocket gameWebSocket = joystickSockets.get(username);
        gameWebSocket.sendCardsToJoystick(correct, message, focusOnCard, cards);
    }

    public void sendScores(GameUser user) {
        GameWebSocket gameWebSocket = userSockets.get(user.getMyName());
        gameWebSocket.sendScores(user.getGameSession().getPlayersList());
        if (joystickSockets.containsKey(user.getMyName())) {
            gameWebSocket = joystickSockets.get(user.getMyName());
            gameWebSocket.sendScores(user.getGameSession().getPlayersList());
        }
    }
}
