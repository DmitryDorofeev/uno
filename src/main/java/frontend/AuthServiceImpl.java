package frontend;

import base.AuthService;
import db.UserProfile;
import db.DBService;
import redis.clients.jedis.Jedis;

public class AuthServiceImpl implements AuthService {
    private DBService dbService;
    private Jedis storage = new Jedis("127.0.0.1");
    public AuthServiceImpl(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public int signIn(String sessionId, String login, String password) {
        UserProfile user = dbService.getUserData(login);
        if (isLoggedIn(sessionId) == 500 && user != null && user.getPass().equals(password)) {
            if (storage.hexists("userSessions", login))
                logOut(storage.hget("userSessions", login));
            storage.hset("sessions", sessionId, login);
            storage.hset("userSessions", login, sessionId);
            return 200;
        }
        return 403;
    }

    @Override
    public int signInByToken(String sessionId, String token, String name) {
        UserProfile user = dbService.getUserData(token);
        if (isLoggedIn(sessionId) == 500) {
            if (user == null) {
                user = new UserProfile(token, name);
                dbService.saveUser(user);
            } else {
                if (storage.hexists("userSessions", token))
                    logOut(storage.hget("userSessions", token));
            }
            storage.hset("sessions", sessionId, token);
            storage.hset("userSessions", token, sessionId);
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
                String login = storage.hget("sessions", sessionId);
                storage.hdel("userSessions", login);
                storage.hdel("sessions", sessionId);
                break;
        }
        return true;
    }

    @Override
    public int isLoggedIn(String sessionId) {
        if (storage.hexists("sessions", sessionId))
            return 200;
        return 500;
    }

    @Override
    public UserProfile getUserProfile(String sessionId) {
        if (isLoggedIn(sessionId) == 200)
            return dbService.getUserData(storage.hget("sessions", sessionId));
        return null;
    }

    @Override
    public long getAmountOfRegisteredUsers() {
        return dbService.getAmountOfRegisteredUsers();
    }

    @Override
    public long getAmountOfUsersOnline() {
        return storage.hlen("sessions");
    }

}
