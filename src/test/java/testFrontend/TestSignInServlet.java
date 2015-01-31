package testFrontend;

import base.AuthService;
import base.SignInServlet;
import db.UserProfile;
import frontend.SignInServletImpl;

import org.json.simple.JSONObject;
import org.junit.*;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by К on 22.12.2014.
 */
public class TestSignInServlet {
    public AuthService testAuthService = mock(AuthService.class);
    public SignInServlet testSignInServlet= new SignInServletImpl(testAuthService);

    @Test
    public void invalidDoGet() throws Exception {
        PrintWriter testPrintWriter = mock(PrintWriter.class);
        HttpSession testSession = mock(HttpSession.class);
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);
        ArgumentCaptor<String> jsonStringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(testRequest.getSession()).thenReturn(testSession);
        when(testSession.getId()).thenReturn("1");
        when(testAuthService.isLoggedIn("1")).thenReturn(500);
        when(testResponse.getWriter()).thenReturn(testPrintWriter);


        testSignInServlet.doGet(testRequest, testResponse);

        verify(testPrintWriter).print(jsonStringArgumentCaptor.capture());

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 500);
        jsonObj.put("message", "User has not logged in");

        assertEquals("Asserting server status for session 1", jsonObj.toJSONString(), jsonStringArgumentCaptor.getValue());
    }

    @Test
    public void goodDoGet() throws Exception {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);
        HttpSession testSession = mock(HttpSession.class);
        PrintWriter testPrintWriter = mock(PrintWriter.class);
        ArgumentCaptor<String> jsonStringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(testRequest.getSession()).thenReturn(testSession);
        when(testSession.getId()).thenReturn("2");
        when(testAuthService.isLoggedIn("2")).thenReturn(200);
        when(testAuthService.getUserProfile("2")).thenReturn(new UserProfile("test", "test", "test"));
        when(testResponse.getWriter()).thenReturn(testPrintWriter);

        testSignInServlet.doGet(testRequest, testResponse);

        verify(testResponse.getWriter()).print(jsonStringArgumentCaptor.capture());

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 200);
        jsonObj.put("login", "test");

        assertEquals("Asserting server status for session 2", jsonObj.toJSONString(), jsonStringArgumentCaptor.getValue());

        verify(testRequest.getSession()).getId();
    }

    @Test
    public void testDoPostUnknownDevice() throws Exception {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);
        HttpSession testSession = mock(HttpSession.class);
        PrintWriter testPrintWriter = mock(PrintWriter.class);
        ArgumentCaptor<String> jsonStringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(testRequest.getSession()).thenReturn(testSession);
        when(testSession.getId()).thenReturn("1");
        when(testAuthService.isLoggedIn("1")).thenReturn(500);
        when(testResponse.getWriter()).thenReturn(testPrintWriter);

        when(testRequest.getParameter("login")).thenReturn("test");
        when(testRequest.getParameter("password")).thenReturn("test");
        when(testRequest.getParameter("extra")).thenReturn("");

        testSignInServlet.doPost(testRequest, testResponse);

        verify(testResponse.getWriter()).print(jsonStringArgumentCaptor.capture());

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 500);
        jsonObj.put("message", "Unknown device");
        assertEquals("Asserting server status for session 2", jsonObj.toJSONString(), jsonStringArgumentCaptor.getValue());
    }

    @Test
    public void testDoPostAlreadyLogged() throws Exception {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);
        HttpSession testSession = mock(HttpSession.class);
        PrintWriter testPrintWriter = mock(PrintWriter.class);
        ArgumentCaptor<String> jsonStringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(testRequest.getSession()).thenReturn(testSession);
        when(testSession.getId()).thenReturn("3");
        when(testAuthService.isLoggedIn("3")).thenReturn(400);
        when(testRequest.getParameter("login")).thenReturn("test");
        when(testRequest.getParameter("password")).thenReturn("test");
        when(testRequest.getParameter("extra")).thenReturn(null);
        when(testResponse.getWriter()).thenReturn(testPrintWriter);

        testSignInServlet.doPost(testRequest, testResponse);

        verify(testResponse.getWriter()).print(jsonStringArgumentCaptor.capture());

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 500);
        jsonObj.put("message", "User has already logged in");
        assertEquals("Asserting server status for session 3", jsonObj.toJSONString(), jsonStringArgumentCaptor.getValue());

        verify(testRequest.getSession()).getId();
    }

    @Test
    public void testDoPostJoystickActivated() throws Exception {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);
        HttpSession testSession = mock(HttpSession.class);
        PrintWriter testPrintWriter = mock(PrintWriter.class);
        ArgumentCaptor<String> jsonStringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(testRequest.getSession()).thenReturn(testSession);
        when(testSession.getId()).thenReturn("4");
        when(testAuthService.isLoggedIn("4")).thenReturn(400);
        when(testRequest.getParameter("login")).thenReturn("test");
        when(testRequest.getParameter("password")).thenReturn("test");
        when(testRequest.getParameter("extra")).thenReturn("joystick");
        when(testResponse.getWriter()).thenReturn(testPrintWriter);

        testSignInServlet.doPost(testRequest, testResponse);

        verify(testResponse.getWriter()).print(jsonStringArgumentCaptor.capture());

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 500);
        jsonObj.put("message", "Joystick for that user has already been activated");
        assertEquals("Asserting server status for session 4", jsonObj.toJSONString(), jsonStringArgumentCaptor.getValue());

        verify(testRequest.getSession()).getId();
    }

    @Test
    public void testDoPostGood() throws Exception {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);
        HttpSession testSession = mock(HttpSession.class);
        PrintWriter testPrintWriter = mock(PrintWriter.class);
        ArgumentCaptor<String> jsonStringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(testRequest.getParameter("login")).thenReturn("test");
        when(testRequest.getParameter("password")).thenReturn("test");
        when(testRequest.getParameter("extra")).thenReturn("joystick");
        when(testRequest.getSession()).thenReturn(testSession);
        when(testSession.getId()).thenReturn("5");
        when(testAuthService.isLoggedIn("5")).thenReturn(500);
        when (testAuthService.signIn("5", "test", "test")).thenReturn(200);
        when(testAuthService.getUserProfile("5")).thenReturn(new UserProfile("test", "test", "test"));
        when(testResponse.getWriter()).thenReturn(testPrintWriter);

        testSignInServlet.doPost(testRequest, testResponse);

        verify(testResponse.getWriter()).print(jsonStringArgumentCaptor.capture());

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 200);
        jsonObj.put("login", "test");
        jsonObj.put("email", "test");

        assertEquals("Asserting server status for session 5", jsonObj.toJSONString(), jsonStringArgumentCaptor.getValue());

        verify(testRequest.getSession()).getId();
    }

    @Test
    public void testDoPostWrongData() throws Exception {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);
        HttpSession testSession = mock(HttpSession.class);
        PrintWriter testPrintWriter = mock(PrintWriter.class);
        ArgumentCaptor<String> jsonStringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(testRequest.getParameter("login")).thenReturn("test");
        when(testRequest.getParameter("password")).thenReturn("test");
        when(testRequest.getParameter("extra")).thenReturn("joystick");

        when(testRequest.getSession()).thenReturn(testSession);
        when(testSession.getId()).thenReturn("6");
        when(testAuthService.isLoggedIn("6")).thenReturn(500);
        when(testAuthService.signIn("6", "test", "test")).thenReturn(403);
        when(testAuthService.getUserProfile("6")).thenReturn(new UserProfile("test", "test", "test"));
        when(testResponse.getWriter()).thenReturn(testPrintWriter);

        testSignInServlet.doPost(testRequest, testResponse);

        verify(testResponse.getWriter()).print(jsonStringArgumentCaptor.capture());

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 500);
        jsonObj.put("message", "Wrong login or password");

        assertEquals("Asserting server status for session 6", jsonObj.toJSONString(), jsonStringArgumentCaptor.getValue());

        verify(testRequest.getSession()).getId();
    }

    @Test
    public void testDoPostNotLogged() throws Exception {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);
        HttpSession testSession = mock(HttpSession.class);
        PrintWriter testPrintWriter = mock(PrintWriter.class);
        ArgumentCaptor<String> jsonStringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(testRequest.getParameter("login")).thenReturn("test");
        when(testRequest.getParameter("password")).thenReturn("test");
        when(testRequest.getParameter("extra")).thenReturn("joystick");

        when(testRequest.getSession()).thenReturn(testSession);
        when(testSession.getId()).thenReturn("7");
        when(testAuthService.isLoggedIn("7")).thenReturn(500);
        when(testAuthService.signIn("7", "test", "test")).thenReturn(404);
        when(testAuthService.getUserProfile("7")).thenReturn(new UserProfile("test", "test", "test"));
        when(testResponse.getWriter()).thenReturn(testPrintWriter);

        testSignInServlet.doPost(testRequest, testResponse);

        verify(testResponse.getWriter()).print(jsonStringArgumentCaptor.capture());

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 500);
        jsonObj.put("message", "User has not logged in");

        assertEquals("Asserting server status for session 7", jsonObj.toJSONString(), jsonStringArgumentCaptor.getValue());

        verify(testRequest.getSession()).getId();
    }

    @Test
    public void testDoPostEmptyLogin() throws Exception {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);
        HttpSession testSession = mock(HttpSession.class);
        PrintWriter testPrintWriter = mock(PrintWriter.class);
        ArgumentCaptor<String> jsonStringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(testRequest.getParameter("login")).thenReturn("");
        when(testRequest.getParameter("password")).thenReturn("test");
        when(testRequest.getParameter("extra")).thenReturn("joystick");

        when(testRequest.getSession()).thenReturn(testSession);
        when(testSession.getId()).thenReturn("8");
        when(testAuthService.isLoggedIn("8")).thenReturn(200);
        when(testResponse.getWriter()).thenReturn(testPrintWriter);

        testSignInServlet.doPost(testRequest, testResponse);

        verify(testResponse.getWriter()).print(jsonStringArgumentCaptor.capture());

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 500);
        jsonObj.put("message", "Not all fields are filled");

        assertEquals("Asserting server status for session 8", jsonObj.toJSONString(), jsonStringArgumentCaptor.getValue());
    }

    @Test
    public void testDoPostEmptyPassword() throws Exception {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);
        HttpSession testSession = mock(HttpSession.class);
        PrintWriter testPrintWriter = mock(PrintWriter.class);
        ArgumentCaptor<String> jsonStringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(testRequest.getParameter("login")).thenReturn("test");
        when(testRequest.getParameter("password")).thenReturn("");
        when(testRequest.getParameter("extra")).thenReturn("joystick");

        when(testRequest.getSession()).thenReturn(testSession);
        when(testSession.getId()).thenReturn("9");
        when(testAuthService.isLoggedIn("9")).thenReturn(200);
        when(testResponse.getWriter()).thenReturn(testPrintWriter);

        testSignInServlet.doPost(testRequest, testResponse);

        verify(testResponse.getWriter()).print(jsonStringArgumentCaptor.capture());

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 500);
        jsonObj.put("message", "Not all fields are filled");

        assertEquals("Asserting server status for session 9", jsonObj.toJSONString(), jsonStringArgumentCaptor.getValue());
    }
}
