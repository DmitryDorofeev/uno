package db;

/**
 * Created by alexey on 22.11.2014.
 */
public interface DBService {
    boolean saveUser(UserProfile user);

    UserProfile getUserData(String login);

    Long getUserIdByName(String login);

    boolean savePlayerScores(long gameId, String name, long score);

    long getPlayerScores(String login);

    long getNewGameId();

    long getAmountOfRegisteredUsers();

    boolean getStatus();
}
