package db;

/**
 * Created by alexey on 22.11.2014.
 */
public interface DBService {
    boolean saveUser(UserProfile user);

    UserProfile getUserData(String login);

    long getPlayerScores(String login);

    long getAmountOfRegisteredUsers();

    boolean getStatus();
}
