package frontend;

import base.AuthService;
import base.GameMechanics;
import base.WebSocketService;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import resources.ResourceSystem;
import utils.LoggerHelper;

import javax.servlet.annotation.WebServlet;

/**
 * This class represents a servlet starting a webSocket application
 */
public class WebSocketGameServlet extends WebSocketServlet {
    public final static String WebSocketGameServletURL = "/game";
    private final static int IDLE_TIME = ResourceSystem.instance().getServerConfigResource().getSocketTimeOut() * 60 * 1000;
    private AuthService authService;

    public WebSocketGameServlet(AuthService authService) {
        LoggerHelper.logJSON("WebSocketGameServlet()", null);
        this.authService = authService;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        LoggerHelper.logJSON("configure()", null);
        factory.getPolicy().setIdleTimeout(IDLE_TIME);
        factory.setCreator(new CustomWebSocketCreator(authService));
    }
}
