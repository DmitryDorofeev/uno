package frontend;

import base.AuthService;
import base.UserProfile;
import db.UserProfileImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AuthServiceImpl implements AuthService {
    private Map<String, UserProfile> users = new HashMap<>();
    private Map<String, String> sessions = new HashMap<>(); // (id, login)
    private Map<String, String> userSessions = new HashMap<>(); // (login, id)


    public AuthServiceImpl() {
        users.put("admin", new UserProfileImpl("admin", "admin", "admin"));
        users.put("test", new UserProfileImpl("test", "test", "test"));
    }

    @Override
    public boolean signIn(String sessionId, String login, String password) {
        if (!isLoggedIn(sessionId) && users.containsKey(login) && users.get(login).getPass().equals(password)) {
            if (userSessions.containsKey(login))
                logOut(userSessions.get(login));
            sessions.put(sessionId, login);
            userSessions.put(login, sessionId);
            return true;
        }
        return false;
    }

    @Override
    public boolean signUp(UserProfile user) {
        if (users.containsKey(user.getLogin()))
            return false;
        users.put(user.getLogin(), user);
        return true;
    }

    @Override
    public void logOut(String sessionId) {
        if (isLoggedIn(sessionId)) {
            userSessions.remove(sessions.get(sessionId));
            sessions.remove(sessionId);
        }
    }

    @Override
    public boolean isLoggedIn(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    @Override
    public UserProfile getUserProfile(String sessionId) {
        if (isLoggedIn(sessionId))
            return users.get(sessions.get(sessionId));
        return null;
    }

    @Override
    public int getAmountOfRegisteredUsers() {
        return users.size();
    }

    @Override
    public int getAmountOfUsersOnline() {
        return sessions.size();
    }

    @Override
    public Map<String, Integer> getScoreboard() {
        Map<String, Integer> result = new HashMap<>();
        Iterator it = users.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, UserProfile> pair = (Map.Entry) it.next();
            result.put(pair.getKey(), pair.getValue().getScores());
        }
        return result;
    }
}
