package db;

import base.UserProfile;

/**
 * Created by alexey on 25.10.2014.
 */
public class UserProfileImpl implements UserProfile{
    private final String login;
    private final String pass;
    private final String email;

    public UserProfileImpl(String login, String pass, String email) {
        this.login = login;
        this.pass = pass;
        this.email = email;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPass() {
        return pass;
    }
}
