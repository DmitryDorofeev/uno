package base;

import db.UserProfile;

import java.util.Map;

public interface AuthService {
    int signIn(String sessionId, String login, String password, String extra);

    boolean signUp(UserProfile user);

    boolean logOut(String sessionId, String extra);

    boolean isLoggedIn(String sessionId);

    UserProfile getUserProfile(String sessionId);

    long getAmountOfRegisteredUsers();

    long getAmountOfUsersOnline();
}
