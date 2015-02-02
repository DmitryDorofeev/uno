package frontend;

import base.AuthService;
import base.GameMechanics;
import base.WebSocketService;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import utils.LoggerHelper;

/**
 * @author alexey
 */
public class CustomWebSocketCreator implements WebSocketCreator {
    private AuthService authService;

    public CustomWebSocketCreator(AuthService authService) {
        LoggerHelper.logJSON("CustomWebSocketCreator()", null);
        this.authService = authService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        LoggerHelper.logJSON("createWebSocket()", null);
        String sessionId = req.getHttpServletRequest().getSession().getId();
        String name = authService.getUserProfile(sessionId).getLogin();
        if (name.isEmpty()) {
            name = authService.getUserProfile(sessionId).getName();
        }
        return new GameWebSocket(name);
    }
}
