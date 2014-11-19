package base;

import java.util.Map;

public interface AuthService {
    boolean signIn(String sessionId, String login, String password);

    boolean signUp(UserProfile user);

//    public void drop_db();

    boolean logOut(String sessionId);

    boolean isLoggedIn(String sessionId);

    UserProfile getUserProfile(String sessionId);

    long getAmountOfRegisteredUsers();

    long getAmountOfUsersOnline();
}
