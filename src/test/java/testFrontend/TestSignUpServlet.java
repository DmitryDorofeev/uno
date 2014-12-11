package testFrontend;

import base.SignUpServlet;
import base.UserProfile;
import db.UserProfileImpl;
import frontend.AuthServiceImpl;
import frontend.SignUpServletImpl;
import org.eclipse.jetty.server.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import static org.mockito.Mockito.*;
/**
 * Created by К on 20.11.2014.
 */
public class TestSignUpServlet {
    public static UserProfile testUsers[] = new UserProfileImpl[5];
    SignUpServlet testSignUpServlet = new SignUpServletImpl(new AuthServiceImpl());
    final private static HttpServletRequest testRequest = mock(HttpServletRequest.class);
//    public class ResponseHttp {
//        private String status = new String();
//        private String login = new String();
////    private String status = new String();
//
//    };
    private HttpServletResponse testResponse = mock(HttpServletResponse.class);

    @BeforeClass
    public void initTestValues() {
        testUsers[0] = new UserProfileImpl("","123qaz!", "nologin");
        testUsers[1] = new UserProfileImpl("nopswd","", "nopassword");
        testUsers[2] = new UserProfileImpl("noemail","12345qaz!", "");
        testUsers[3] = new UserProfileImpl("test","test", "test");
        testUsers[4] = new UserProfileImpl("testnouser","testnouser", "testnouser");
    }

    @Test
    public void testDoPost() throws Exception {
        //when(testResponse.getWriter()).thenReturn(jsonObj.toJSONString());
        for (int i = 0; i < 5; ++i) {
            when(testRequest.getParameter("login")).thenReturn(testUsers[i].getLogin());
            when(testRequest.getParameter("email")).thenReturn(testUsers[i].getEmail());
            when(testRequest.getParameter("password")).thenReturn(testUsers[i].getPass());

            testSignUpServlet.doPost(testRequest, testResponse);
        }

        verify(testRequest, times(5)).getParameter("login");
        verify(testRequest, times(5)).getParameter("email");
        verify(testRequest, times(5)).getParameter("password");
    }

}
