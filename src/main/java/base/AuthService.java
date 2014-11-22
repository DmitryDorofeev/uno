package base;

import db.UserProfile;

import java.util.Map;

public interface AuthService {
    boolean signIn(String sessionId, String login, String password);

    boolean signUp(UserProfile user);

    boolean logOut(String sessionId);

    boolean isLoggedIn(String sessionId);

    UserProfile getUserProfile(String sessionId);

    long getAmountOfRegisteredUsers();

    long getAmountOfUsersOnline();
}
