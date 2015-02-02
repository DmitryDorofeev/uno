package db;

/**
 * Created by alexey on 25.10.2014.
 */
public class UserProfile {
    private final String login;
    private final String pass;
    private final String email;
    private final String token;
    private final String name;

    public UserProfile(String login, String pass, String email) {
        this.login = login;
        this.pass = pass;
        this.email = email;
        this.token = "";
        this.name = "";
    }

    public UserProfile(String token, String name) {
        this.login = "";
        this.pass = "";
        this.email = "";
        this.token = token;
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }
}
