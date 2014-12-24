package testadmin;

import admin.AdminPageServletImpl;
import base.AdminPageServlet;
import base.AuthService;
import db.UserProfile;
import frontend.AuthServiceImpl;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mockito.ArgumentCaptor;
import utils.TimeHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by К on 24.12.2014.
 */
public class TestAdminPageServlet {
    private static Server testServer;
    public static AuthService testAS = mock(AuthService.class);
    public static AdminPageServletImpl testAdmin = new AdminPageServletImpl(testAS);

    @BeforeClass
    public static void startServer() throws Exception {
        testServer = new Server();
        ServerConnector testCon = new ServerConnector(testServer);
        testCon.setPort(9000);
        testServer.addConnector(testCon);

        ServletContextHandler testContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        testContext.addServlet(new ServletHolder(testAdmin), AdminPageServlet.adminPageURL);

        testServer.start();
    }

    public boolean isAlive() {
        boolean isAlive = true;
        try {
            URL hp = new URL("http://127.0.0.1:9000");
            URLConnection hpCon = hp.openConnection();
        }
        catch (Exception e) {
            isAlive = false;
            return isAlive;
        }
        return isAlive;
    }

    @Test
    public void testStoppingServer() throws Exception {
        Integer timeout = new Integer(3000);
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);
        PrintWriter testPrintWriter = mock(PrintWriter.class);
        HttpSession testSession = mock(HttpSession.class);
        ArgumentCaptor<String> jsonStringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(testRequest.getParameter("shutdown")).thenReturn(timeout.toString());
        when(testRequest.getSession()).thenReturn(testSession);
        when(testSession.getId()).thenReturn("1");
        when(testAS.getUserProfile("1")).thenReturn(new UserProfile("admin", "admin", "admin"));
        when(testResponse.getWriter()).thenReturn(testPrintWriter);

        testAdmin.doGet(testRequest, testResponse);

        TimeHelper.sleep(timeout.intValue());

        assertEquals("Is server alive?", false, isAlive());
    }
}
