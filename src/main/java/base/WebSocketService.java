package base;

import MessageSystem.Abonent;
import frontend.GameWebSocket;
import mechanics.GameUser;
import resources.CardResource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author alexey
 */
public interface WebSocketService extends Abonent {

    void addUser(GameWebSocket user, String extra);

    void removeUser(GameWebSocket user, String extra);

    void notifyStartGame(GameUser user);

    void sendCards(GameUser user);

    void notifyGameStep(boolean correct, String message, GameUser user, boolean fromJoystick);

    void notifyChangeFocus(GameUser user);

    void notifyUnoFail(String message, GameUser user);

    void sendCardsToJoystick(boolean correct, String message, String username,
                             long focusOnCard, List<CardResource> cards);

    void sendScores(GameUser user);
}
