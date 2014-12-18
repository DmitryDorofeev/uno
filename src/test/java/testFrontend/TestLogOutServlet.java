package testFrontend;

import base.AuthService;
import base.LogOutServlet;
import frontend.LogOutServletImpl;
import org.json.simple.JSONObject;
import org.junit.*;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
/**
 * Created by К on 13.12.2014.
 */
public class TestLogOutServlet {
    private AuthService testAS = mock(AuthService.class);
    public LogOutServlet testLogOutServlet = new LogOutServletImpl(testAS);

    @Test
    public void invalidDoPost() throws Exception {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);
        PrintWriter testPrintWriter = mock(PrintWriter.class);
        HttpSession testSession = mock(HttpSession.class);
        ArgumentCaptor<String> jsonStringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(testRequest.getSession()).thenReturn(testSession);
        when(testSession.getId()).thenReturn("1");
        when(testAS.isLoggedIn("1")).thenReturn(500);
        when(testResponse.getWriter()).thenReturn(testPrintWriter);

        testLogOutServlet.doPost(testRequest, testResponse);

        verify(testResponse.getWriter()).print(jsonStringArgumentCaptor.capture());

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 500);
        jsonObj.put("message", "User was not logged in");

        assertEquals("Asserting server status for session 1", jsonObj.toJSONString(), jsonStringArgumentCaptor.getValue());

        verify(testRequest.getSession()).getId();
    }

    @Test
    public void goodDoPost() throws Exception {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);
        HttpSession testSession = mock(HttpSession.class);
        PrintWriter testPrintWriter = mock(PrintWriter.class);
        ArgumentCaptor<String> jsonStringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(testRequest.getSession()).thenReturn(testSession);
        when(testSession.getId()).thenReturn("2");
        when(testAS.isLoggedIn("2")).thenReturn(200);
        when(testResponse.getWriter()).thenReturn(testPrintWriter);

        testLogOutServlet.doPost(testRequest, testResponse);

        verify(testResponse.getWriter()).print(jsonStringArgumentCaptor.capture());

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", 200);

        assertEquals("Asserting server status for session 2", jsonObj.toJSONString(), jsonStringArgumentCaptor.getValue());

        verify(testRequest.getSession()).getId();
    }
}
