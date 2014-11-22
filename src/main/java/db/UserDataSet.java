package db;

import db.UserProfile;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Created by alexey on 16.11.2014.
 */

@Entity
@Table(name = "user")
public class UserDataSet implements Serializable { // Serializable Important to Hibernate!
    private static final long serialVersionUID = 0L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "login")
    private String login;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    //Important to Hibernate!
    public UserDataSet() {
    }

    public UserDataSet(long id, UserProfile user){
        setId(id);
        setLogin(user.getLogin());
        setEmail(user.getEmail());
        setPassword(user.getPass());
    }

    public UserDataSet(UserProfile user){
        setId(-1);
        setLogin(user.getLogin());
        setEmail(user.getEmail());
        setPassword(user.getPass());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
