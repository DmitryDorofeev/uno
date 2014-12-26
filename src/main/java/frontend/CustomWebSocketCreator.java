package frontend;

import base.AuthService;
import base.GameMechanics;
import base.WebSocketService;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

/**
 * @author alexey
 */
public class CustomWebSocketCreator implements WebSocketCreator {
    private AuthService authService;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public CustomWebSocketCreator(AuthService authService) {
        System.out.println("CustomWebSocketCreator()");
        this.authService = authService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        System.out.println("createWebSocket()");
        String sessionId = req.getHttpServletRequest().getSession().getId();
        String name = authService.getUserProfile(sessionId).getLogin();
        return new GameWebSocket(name);
    }
}
