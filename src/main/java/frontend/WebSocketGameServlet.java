package frontend;

import base.AuthService;
import base.GameMechanics;
import base.WebSocketService;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import resources.ResourceSystem;

import javax.servlet.annotation.WebServlet;

/**
 * This class represents a servlet starting a webSocket application
 */
public class WebSocketGameServlet extends WebSocketServlet {
    public final static String WebSocketGameServletURL = "/game";
    private final static int IDLE_TIME = ResourceSystem.instance().getServerConfigResource().getSocketTimeOut() * 60 * 1000;
    private AuthService authService;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public WebSocketGameServlet(AuthService authService,
                                GameMechanics gameMechanics,
                                WebSocketService webSocketService) {
        System.out.println("WebSocketGameServlet()");
        this.authService = authService;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        System.out.println("configure()");
        factory.getPolicy().setIdleTimeout(IDLE_TIME);
        factory.setCreator(new CustomWebSocketCreator(authService, gameMechanics, webSocketService));
    }
}
