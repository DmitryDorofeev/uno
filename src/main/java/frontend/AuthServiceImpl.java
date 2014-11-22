package frontend;

import base.AuthService;
import db.UserProfile;
import db.DBService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AuthServiceImpl implements AuthService {
    private Map<String, String> sessions = new HashMap<>(); // (id, login)
    private Map<String, String> userSessions = new HashMap<>(); // (login, id)
    private DBService dbService;

    public AuthServiceImpl(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public boolean signIn(String sessionId, String login, String password) {
        UserProfile user = dbService.getUserData(login);
        if (!isLoggedIn(sessionId) && user != null && user.getPass().equals(password)) {
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
        return !(user.getLogin().isEmpty() || user.getEmail().isEmpty() || user.getPass().isEmpty())
                && dbService.saveUser(user);
    }

    @Override
    public boolean logOut(String sessionId) {
        if (isLoggedIn(sessionId)) {
            userSessions.remove(sessions.get(sessionId));
            sessions.remove(sessionId);
            return true;
        }
        return false;
    }

    @Override
    public boolean isLoggedIn(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    @Override
    public UserProfile getUserProfile(String sessionId) {
        if (isLoggedIn(sessionId))
            return dbService.getUserData(sessions.get(sessionId));
        return null;
    }

    @Override
    public long getAmountOfRegisteredUsers() {
        return dbService.getAmountOfRegisteredUsers();
    }

    @Override
    public long getAmountOfUsersOnline() {
        return sessions.size();
    }

//    @Override
//    public Map<String, Integer> getScoreboard() {
//        Map<String, Integer> result = new HashMap<>();
//        Iterator it = users.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry<String, UserProfile> pair = (Map.Entry) it.next();
//            //result.put(pair.getKey(), pair.getValue().getScores());
//        }
//        return result;
//    }
}
