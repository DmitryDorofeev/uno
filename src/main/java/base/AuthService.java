package base;

import db.UserProfile;

import java.util.Map;

public interface AuthService {
    int signIn(String sessionId, String login, String password);

    int signInByToken(String sessionId, String token);

    boolean signUp(UserProfile user);

    boolean logOut(String sessionId);

    int isLoggedIn(String sessionId);

    UserProfile getUserProfile(String sessionId);

    long getAmountOfRegisteredUsers();

    long getAmountOfUsersOnline();
}
