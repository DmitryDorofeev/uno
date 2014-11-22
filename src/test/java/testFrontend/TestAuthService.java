package testFrontend;

import static org.junit.Assert.*;

import base.AuthService;
import db.*;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import frontend.AuthServiceImpl;
import resources.ResourceSystem;

import java.beans.Statement;
import java.sql.*;

/**
 * Created by К on 30.10.2014.
 */
public class TestAuthService {
    public static AuthService testAuthService;
    public static UserProfile testUsers[] = new UserProfile[13];

    public void initSignUp() {
        testAuthService.signUp(testUsers[8]);
        testAuthService.signUp(testUsers[3]);
        testAuthService.signUp(testUsers[7]);
    }

    public void initSignIn() {
        testAuthService.signIn("11", testUsers[8].getLogin(), testUsers[8].getPass());
        testAuthService.signIn("5", testUsers[3].getLogin(), testUsers[3].getPass());
    }

    @BeforeClass
    public static void initTestValues() {
        testAuthService = new AuthServiceImpl(new DBServiceImpl());
        testUsers[0] = new UserProfile("","123qaz!", "nologin");
        testUsers[1] = new UserProfile("nopswd","", "nopassword");
        testUsers[2] = new UserProfile("noemail","12345qaz!", "");
        testUsers[3] = new UserProfile("test","test", "test");
        testUsers[4] = new UserProfile("nosuchuser","12345qaz!", "rubicon");
        testUsers[5] = new UserProfile("test", "", "test");
        testUsers[6] = new UserProfile("","test", "test");
        testUsers[7] = new UserProfile("gooduser","qazxswedc123", "good");
        testUsers[8] = new UserProfile("goodlogin", "goodpasswd", "goodemail");
        testUsers[9] = new UserProfile("testreg1", "testreg1", "testreg1");
        testUsers[10] = new UserProfile("testreg2", "testreg2", "testreg2");
        testUsers[11] = new UserProfile("testreg1", "testreg3", "testreg3");
        testUsers[12] = new UserProfile("testreg4", "testreg4", "testreg1");
    }

    @Test
    public void testSignUp() {
        assertEquals("No registered nologin", false, testAuthService.signUp(testUsers[0]));
        assertEquals("No registered nopswd", false, testAuthService.signUp(testUsers[1]));
        assertEquals("No registered noemail", false, testAuthService.signUp(testUsers[2]));
        assertEquals("Registered testreg1", true, testAuthService.signUp(testUsers[9]));
        assertEquals("Already registered testreg1", false, testAuthService.signUp(testUsers[9]));
        assertEquals("Registered testreg2", true, testAuthService.signUp(testUsers[10]));
        assertEquals("No registered testreg3", false, testAuthService.signUp(testUsers[11]));
        assertEquals("No registered testreg4", false, testAuthService.signUp(testUsers[12]));
    }

    @Test
    public void testSignIn() {
        initSignUp();
        long usersBT = testAuthService.getAmountOfUsersOnline();
        assertEquals("No such user " + testUsers[2].getLogin(), false, testAuthService.signIn("1", testUsers[2].getLogin(), testUsers[2].getPass()));
        assertEquals("No such user", false, testAuthService.signIn("2", testUsers[4].getLogin(), testUsers[4].getPass()));
        assertEquals("No logined: password is empty", false, testAuthService.signIn("3", testUsers[5].getLogin(), testUsers[5].getPass()));
        assertEquals("No logined: login is empty", false, testAuthService.signIn("4", testUsers[6].getLogin(), testUsers[6].getPass()));
        assertEquals("Logined " + testUsers[3].getLogin(), true, testAuthService.signIn("5", testUsers[3].getLogin(), testUsers[3].getPass()));
        assertEquals("Logined " + testUsers[8].getLogin() + "session 7", true, testAuthService.signIn("7", testUsers[8].getLogin(), testUsers[8].getPass()));
        assertEquals("Already logined " + testUsers[8].getLogin(), false, testAuthService.signIn("7", testUsers[8].getLogin(), testUsers[8].getPass()));
        assertEquals("Logined " + testUsers[8].getLogin() + "session 11", true, testAuthService.signIn("11", testUsers[8].getLogin(), testUsers[8].getPass()));
        assertEquals(usersBT + 2, testAuthService.getAmountOfUsersOnline());
    }

    @Test
    public void testLogOut() {
        initSignUp();
        initSignIn();
        long userBT = testAuthService.getAmountOfUsersOnline();
        assertEquals("No logined user with SessionId 1", false, testAuthService.logOut("1"));
        assertEquals("No logined user with SessionId 2", false, testAuthService.logOut("2"));
        assertEquals("No logined user with SessionId 3", false, testAuthService.logOut("3"));
        assertEquals("No logined user with SessionId 4", false, testAuthService.logOut("4"));
        assertEquals("Logout " + testUsers[3].getLogin() + " SessionId 5", true, testAuthService.logOut("5"));
        assertEquals("No logined user with SessionId 6", false, testAuthService.logOut("6"));
        assertEquals("No logined user with SessionId 7", false, testAuthService.logOut("7"));
        assertEquals("Logout " + testUsers[8].getLogin() + " SessionId 11", true, testAuthService.logOut("11"));
        assertEquals(userBT - 2, testAuthService.getAmountOfUsersOnline());
    }
}
