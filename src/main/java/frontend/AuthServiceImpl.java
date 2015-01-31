package frontend;

import base.AuthService;
import db.UserProfile;
import db.DBService;

import javax.jws.soap.SOAPBinding;
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
    public int signIn(String sessionId, String login, String password) {
        UserProfile user = dbService.getUserData(login);
        if (isLoggedIn(sessionId) == 500 && user != null && user.getPass().equals(password)) {
            if (userSessions.containsKey(login))
                logOut(userSessions.get(login));
            sessions.put(sessionId, login);
            userSessions.put(login, sessionId);
            return 200;
        }
        return 403;
    }

    @Override
    public int signInByToken(String sessionId, String token, String name) {
        UserProfile user = dbService.getUserDataByToken(token);
        if (isLoggedIn(sessionId) == 500) {
            if (user == null) {
                user = new UserProfile(token, name);
                dbService.saveUser(user);
            } else {
                if (userSessions.containsKey(token))
                    logOut(userSessions.get(token));
            }
            sessions.put(sessionId, token);
            userSessions.put(token, sessionId);
            return 200;
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
        }
        return true;
    }

    @Override
    public int isLoggedIn(String sessionId) {
        if (sessions.containsKey(sessionId))
            return 200;
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
