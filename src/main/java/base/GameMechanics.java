package base;

import resources.CardResource;

/**
 * @author alexey
 */
public interface GameMechanics {

    void addUser(String user, long playersCount);

    void gameStep(String user, long cartId);
}
