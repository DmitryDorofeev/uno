package testFrontend;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import db.UserProfileImpl;
import frontend.AuthServiceImpl;

/**
 * Created by К on 30.10.2014.
 */
public class TestAuthService {
    public AuthServiceImpl testAuthService = new AuthServiceImpl();
    public UserProfileImpl testUsers[] = new UserProfileImpl[8];

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
    }

    @Test
    public void testSignUp() {
        initTestValues();
        assertEquals("No registered nologin", false, testAuthService.signUp(testUsers[0]));
        assertEquals("No registered nopswd", false, testAuthService.signUp(testUsers[1]));
        assertEquals("Registered noemail", true, testAuthService.signUp(testUsers[2]));
        assertEquals("Already registered test", false, testAuthService.signUp(testUsers[3]));
        assertEquals("Registered gooduser", true, testAuthService.signUp(testUsers[7]));
    }

    @Test
    public void testSignInAndSighOut() {
        initTestValues();
        assertEquals("Logined" + testUsers[2].getLogin(), true, testAuthService.signIn("1", testUsers[2].getLogin(), testUsers[2].getPass()));
        assertEquals("No such user", false, testAuthService.signIn("2", testUsers[4].getLogin(), testUsers[4].getPass()));
        assertEquals("No logined: password is empty", false, testAuthService.signIn("3", testUsers[5].getLogin(), testUsers[5].getPass()));
        assertEquals("No logined: login is empty", false, testAuthService.signIn("4", testUsers[6].getLogin(), testUsers[6].getPass()));
        assertEquals("Logined" + testUsers[3].getLogin(), true, testAuthService.signIn("5", testUsers[3].getLogin(), testUsers[3].getPass()));

        assertEquals("Logout" + testUsers[2].getLogin(), true, testAuthService.logOut("1"));
        assertEquals("No logined user with this SessionId", false, testAuthService.logOut("2"));
        assertEquals("No logined user with this SessionId", false, testAuthService.logOut("3"));
        assertEquals("No logined user with this SessionId", false, testAuthService.logOut("4"));
        assertEquals("Logout" + testUsers[3].getLogin(), false, testAuthService.logOut("5"));
        assertEquals("No logined user with this SessionId", false, testAuthService.logOut("6"));
    }

}
