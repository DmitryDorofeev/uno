package base;

import resources.CardResource;

/**
 * @author alexey
 */
public interface GameMechanics {

    void addUser(String username, long playersCount);

    void removeUser(String username);

    void gameStep(String username, long focusOnCard, String newColor);

    void initJoystick(String username);

    void stepByJoystick(String username, String action, String newColor);

    public boolean isPlayerInWaiters(String login);
}
