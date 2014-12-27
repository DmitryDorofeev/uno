package base;

import MessageSystem.Abonent;
import resources.CardResource;

/**
 * @author alexey
 */
public interface GameMechanics extends Abonent {

    void addUser(String username, long playersCount);

    void removeUser(String username);

    void gameStep(String username, long focusOnCard, String newColor, String fromJoystick);

    void initJoystick(String username);

    void stepByJoystick(String username, String action, String newColor);

    void doUno(String username);

    public boolean isPlayerInWaiters(String login);
}
