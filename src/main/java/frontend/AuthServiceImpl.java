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
    private Map<String, String> joystickUser = new HashMap<>(); // (id, id)
    private Map<String, String> userJoystick = new HashMap<>(); // (login, id)
    private DBService dbService;

    public AuthServiceImpl(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public int signIn(String sessionId, String login, String password, String extra) {
        UserProfile user = dbService.getUserData(login);
        if (!isLoggedIn(sessionId) && user != null && user.getPass().equals(password)) {
            if (extra == null) {
                if (userSessions.containsKey(login))
                    logOut(userSessions.get(login), null);
                sessions.put(sessionId, login);
                userSessions.put(login, sessionId);
                return 200;
            }
            else if (extra.equals("joystick")) {
                if (getUserProfile(userSessions.get(login)) != null) {
                    if (userJoystick.containsKey(login))
                        logOut(userJoystick.get(login), extra);
                    joystickUser.put(sessionId, userSessions.get(login));
                    userJoystick.put(login, sessionId);
                    return 200;
                }
                return 404;
            }
        }
        return 403;
    }

    @Override
    public boolean signUp(UserProfile user) {
        return !(user.getLogin().isEmpty() || user.getEmail().isEmpty() || user.getPass().isEmpty())
                && dbService.saveUser(user);
    }

    @Override
    public boolean logOut(String sessionId, String extra) {
        if (isLoggedIn(sessionId)) {
            if (extra == null) {
                userSessions.remove(sessions.get(sessionId));
                sessions.remove(sessionId);
            }
            userJoystick.remove(sessions.get(joystickUser.get(sessionId)));
            joystickUser.remove(sessionId);
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
        else {
            String userSessionId = joystickUser.get(sessionId);
            if (isLoggedIn(userSessionId))
                return dbService.getUserData(sessions.get(userSessionId));
        }
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
