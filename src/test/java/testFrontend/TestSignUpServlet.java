package testFrontend;

import base.SignUpServlet;
import base.UserProfile;
import db.UserProfileImpl;
import frontend.AuthServiceImpl;
import frontend.SignUpServletImpl;
import org.json.simple.JSONObject;
import org.junit.*;
import static org.junit.Assert.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;
import org.mockito.ArgumentCaptor;

import java.io.PrintWriter;

/**
 * Created by К on 20.11.2014.
 */
public class TestSignUpServlet {
    public static UserProfile testUsers[] = new UserProfileImpl[6];
    SignUpServlet testSignUpServlet = new SignUpServletImpl(new AuthServiceImpl());

    @BeforeClass
    public static void initTestValues() {
        testUsers[0] = new UserProfileImpl("","123qaz!", "nologin");
        testUsers[1] = new UserProfileImpl("nopswd","", "nopassword");
        testUsers[2] = new UserProfileImpl("noemail","12345qaz!", "");
        testUsers[3] = new UserProfileImpl("test","test", "test");
        testUsers[4] = new UserProfileImpl("test","test", "test");
    }

    @Test
    public void nologinDoPost() throws Exception {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);
        PrintWriter testPrintWriter = mock(PrintWriter.class);
        ArgumentCaptor<String> jsonStringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(testResponse.getWriter()).thenReturn(testPrintWriter);
        when(testRequest.getParameter("login")).thenReturn(testUsers[0].getLogin());
        when(testRequest.getParameter("email")).thenReturn(testUsers[0].getEmail());
        when(testRequest.getParameter("password")).thenReturn(testUsers[0].getPass());

        testSignUpServlet.doPost(testRequest, testResponse);
        verify(testResponse.getWriter()).print(jsonStringArgumentCaptor.capture());

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 500);
        jsonObj.put("message", "Not all fields are filled");

        assertEquals("Asserting server status for " + testUsers[0].getLogin(), jsonObj.toJSONString(), jsonStringArgumentCaptor.getValue());

        verify(testRequest).getParameter("login");
        verify(testRequest).getParameter("email");
        verify(testRequest).getParameter("password");
    }

    @Test
    public void nopassDoPost() throws Exception {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);
        PrintWriter testPrintWriter = mock(PrintWriter.class);
        ArgumentCaptor<String> jsonStringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(testResponse.getWriter()).thenReturn(testPrintWriter);
        when(testRequest.getParameter("login")).thenReturn(testUsers[1].getLogin());
        when(testRequest.getParameter("email")).thenReturn(testUsers[1].getEmail());
        when(testRequest.getParameter("password")).thenReturn(testUsers[1].getPass());

        testSignUpServlet.doPost(testRequest, testResponse);
        verify(testResponse.getWriter()).print(jsonStringArgumentCaptor.capture());

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 500);
        jsonObj.put("message", "Not all fields are filled");

        assertEquals("Asserting server status for " + testUsers[1].getLogin(), jsonObj.toJSONString(), jsonStringArgumentCaptor.getValue());

        verify(testRequest).getParameter("login");
        verify(testRequest).getParameter("email");
        verify(testRequest).getParameter("password");
    }

    @Test
    public void noemailDoPost() throws Exception {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);
        PrintWriter testPrintWriter = mock(PrintWriter.class);
        ArgumentCaptor<String> jsonStringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(testResponse.getWriter()).thenReturn(testPrintWriter);
        when(testRequest.getParameter("login")).thenReturn(testUsers[2].getLogin());
        when(testRequest.getParameter("email")).thenReturn(testUsers[2].getEmail());
        when(testRequest.getParameter("password")).thenReturn(testUsers[2].getPass());

        testSignUpServlet.doPost(testRequest, testResponse);
        verify(testResponse.getWriter()).print(jsonStringArgumentCaptor.capture());

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 500);
        jsonObj.put("message", "Not all fields are filled");

        assertEquals("Asserting server status for " + testUsers[2].getLogin(), jsonObj.toJSONString(), jsonStringArgumentCaptor.getValue());

        verify(testRequest).getParameter("login");
        verify(testRequest).getParameter("email");
        verify(testRequest).getParameter("password");
    }

    @Test
    public void goodDoPost() throws Exception {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);
        PrintWriter testPrintWriter = mock(PrintWriter.class);
        ArgumentCaptor<String> jsonStringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(testResponse.getWriter()).thenReturn(testPrintWriter);
        when(testRequest.getParameter("login")).thenReturn(testUsers[3].getLogin());
        when(testRequest.getParameter("email")).thenReturn(testUsers[3].getEmail());
        when(testRequest.getParameter("password")).thenReturn(testUsers[3].getPass());

        testSignUpServlet.doPost(testRequest, testResponse);
        verify(testResponse.getWriter()).print(jsonStringArgumentCaptor.capture());

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 200);

        assertEquals("Asserting server status for " + testUsers[3].getLogin(), jsonObj.toJSONString(), jsonStringArgumentCaptor.getValue());

        verify(testRequest).getParameter("login");
        verify(testRequest).getParameter("email");
        verify(testRequest).getParameter("password");
    }

    @Test
    public void replyDoPost() throws Exception {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);
        PrintWriter testPrintWriter = mock(PrintWriter.class);
        ArgumentCaptor<String> jsonStringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(testResponse.getWriter()).thenReturn(testPrintWriter);
        when(testRequest.getParameter("login")).thenReturn(testUsers[4].getLogin());
        when(testRequest.getParameter("email")).thenReturn(testUsers[4].getEmail());
        when(testRequest.getParameter("password")).thenReturn(testUsers[4].getPass());

        testSignUpServlet.doPost(testRequest, testResponse);
        verify(testResponse.getWriter()).print(jsonStringArgumentCaptor.capture());

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 500);
        jsonObj.put("message", "Player with login " + testUsers[4].getLogin() + " is already registered");

        assertEquals("Asserting server status for " + testUsers[4].getLogin(), jsonObj.toJSONString(), jsonStringArgumentCaptor.getValue());

        verify(testRequest).getParameter("login");
        verify(testRequest).getParameter("email");
        verify(testRequest).getParameter("password");
    }
}
