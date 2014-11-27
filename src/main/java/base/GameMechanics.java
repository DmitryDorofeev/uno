package base;

import resources.CardResource;

/**
 * @author alexey
 */
public interface GameMechanics {

    void addUser(String username, long playersCount);

    void removeUser(String username);

    void gameStep(String username, long cartId);
}
