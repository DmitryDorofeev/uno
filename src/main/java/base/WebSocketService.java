package base;

import frontend.GameWebSocket;
import mechanics.GameUser;

/**
 * @author v.chibrikov
 */
public interface WebSocketService {

    void addUser(GameWebSocket user);

    void notifyMyNewScore(GameUser user);

    void notifyEnemyNewScore(GameUser user);

    void notifyStartGame(GameUser user);

    void notifyGameOver(GameUser user, boolean win);
}
