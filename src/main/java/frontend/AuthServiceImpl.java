package frontend;

import base.AuthService;
import db.UserProfile;
import db.DBService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AuthServiceImpl implements AuthService {
    private Map<String, String> sessions = new ConcurrentHashMap<>(); // (id, login)
    private Map<String, String> userSessions = new ConcurrentHashMap<>(); // (login, id)
    private Map<String, String> joystickUser = new ConcurrentHashMap<>(); // (id, id)
    private Map<String, String> userJoystick = new ConcurrentHashMap<>(); // (login, id)
    private DBService dbService;

    public AuthServiceImpl(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public int signIn(String sessionId, String login, String password, String extra) {
        UserProfile user = dbService.getUserData(login);
        if (isLoggedIn(sessionId) == 500 && user != null && user.getPass().equals(password)) {
            if (extra == null) {
                if (userSessions.containsKey(login))
                    logOut(userSessions.get(login));
                sessions.put(sessionId, login);
                userSessions.put(login, sessionId);
                return 200;
            }
            else if (extra.equals("joystick")) {
                if (userSessions.containsKey(login)) {
                    if (userJoystick.containsKey(login))
                        logOut(userJoystick.get(login));
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
    public boolean logOut(String sessionId) {
        switch (isLoggedIn(sessionId)) {
            case 500:
                return false;
            case 200:
                String login = sessions.get(sessionId);
                if (userJoystick.containsKey(login)) {
                    joystickUser.remove(userJoystick.get(login));
                    userJoystick.remove(login);
                }
                userSessions.remove(login);
                sessions.remove(sessionId);
                break;
            case 2000:
                userJoystick.remove(sessions.get(joystickUser.get(sessionId)));
                joystickUser.remove(sessionId);
                break;
        }
        return true;
    }

    @Override
    public int isLoggedIn(String sessionId) {
        if (sessions.containsKey(sessionId))
            return 200;
        if (joystickUser.containsKey(sessionId))
            return 2000;
        return 500;
    }

    @Override
    public UserProfile getUserProfile(String sessionId) {
        if (isLoggedIn(sessionId) == 200)
            return dbService.getUserData(sessions.get(sessionId));
        else {
            String userSessionId = joystickUser.get(sessionId);
            if (isLoggedIn(userSessionId) == 200)
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
}
