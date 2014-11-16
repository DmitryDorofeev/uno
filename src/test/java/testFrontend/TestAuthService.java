package testFrontend;

import static org.junit.Assert.*;

import base.AuthService;
import base.UserProfile;
import org.junit.Before;
import org.junit.Test;

import db.UserProfileImpl;
import frontend.AuthServiceImpl;

/**
 * Created by К on 30.10.2014.
 */
public class TestAuthService {
    public AuthService testAuthService = new AuthServiceImpl();
    public UserProfile testUsers[] = new UserProfileImpl[9];

    public void initSignUp() {
        testAuthService.signUp(testUsers[8]);
        testAuthService.signUp(testUsers[3]);
        testAuthService.signUp(testUsers[7]);
    }

    public void initSignIn() {
        testAuthService.signIn("11", testUsers[8].getLogin(), testUsers[8].getPass());
        testAuthService.signIn("5", testUsers[3].getLogin(), testUsers[3].getPass());
    }

    @Before
    public void initTestValues() {
        testUsers[0] = new UserProfileImpl("","123qaz!", "nologin");
        testUsers[1] = new UserProfileImpl("nopswd","", "nopassword");
        testUsers[2] = new UserProfileImpl("noemail","12345qaz!", "");
        testUsers[3] = new UserProfileImpl("test","test", "test");
        testUsers[4] = new UserProfileImpl("nosuchuser","12345qaz!", "rubicon");
        testUsers[5] = new UserProfileImpl("test", "", "test");
        testUsers[6] = new UserProfileImpl("","test", "test");
        testUsers[7] = new UserProfileImpl("gooduser","qazxswedc123", "good");
        testUsers[8] = new UserProfileImpl("goodlogin", "goodpasswd", "goodemail");
    }

    @Test
    public void testSignUp() {
        assertEquals("No registered nologin", false, testAuthService.signUp(testUsers[0]));
        assertEquals("No registered nopswd", false, testAuthService.signUp(testUsers[1]));
        assertEquals("No registered noemail", false, testAuthService.signUp(testUsers[2]));
        assertEquals("Registered test", true, testAuthService.signUp(testUsers[3]));
        assertEquals("Already registered test", false, testAuthService.signUp(testUsers[3]));
        assertEquals("Registered gooduser", true, testAuthService.signUp(testUsers[7]));
        assertEquals("Registered goodlogin", true, testAuthService.signUp(testUsers[8]));
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
        assertEquals("Logined " + testUsers[8].getLogin(), false, testAuthService.signIn("7", testUsers[8].getLogin(), testUsers[8].getPass()));
        assertEquals("Already logined " + testUsers[8].getLogin(), false, testAuthService.signIn("7", testUsers[8].getLogin(), testUsers[8].getPass()));
        assertEquals("Logined " + testUsers[8].getLogin(), true, testAuthService.signIn("11", testUsers[8].getLogin(), testUsers[8].getPass()));
        assertEquals(usersBT + 2, testAuthService.getAmountOfUsersOnline());
    }

    @Test
    public void testLogOut() {
        initSignUp();
        initSignIn();
        long userBT = testAuthService.getAmountOfUsersOnline();
        assertEquals("No logined user with this SessionId", false, testAuthService.logOut("1"));
        assertEquals("No logined user with this SessionId", false, testAuthService.logOut("2"));
        assertEquals("No logined user with this SessionId", false, testAuthService.logOut("3"));
        assertEquals("No logined user with this SessionId", false, testAuthService.logOut("4"));
        assertEquals("Logout" + testUsers[3].getLogin(), true, testAuthService.logOut("5"));
        assertEquals("No logined user with this SessionId", false, testAuthService.logOut("6"));
        assertEquals("No logined user with this SessionId", false, testAuthService.logOut("7"));
        assertEquals("Logout " + testUsers[8].getLogin(), true, testAuthService.logOut("11"));
        assertEquals(userBT - 2, testAuthService.getAmountOfUsersOnline());
    }
}
