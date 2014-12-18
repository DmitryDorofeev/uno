package base;

import frontend.GameWebSocket;
import mechanics.GameUser;
import resources.CardResource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author alexey
 */
public interface WebSocketService {

    void addUser(GameWebSocket user, String extra);

    void removeUser(GameWebSocket user, String extra);

    void notifyStartGame(GameUser user);

    void sendStartCards(GameUser user);

    void notifyGameStep(boolean correct, String message, GameUser user);

    void notifyChangeFocus(GameUser user);

    void sendCardsToJoystick(boolean correct, String message, String username,
                             long focusOnCard, List<CardResource> cards);
}
