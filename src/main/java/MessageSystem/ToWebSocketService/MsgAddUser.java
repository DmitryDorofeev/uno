package MessageSystem.ToWebSocketService;

import MessageSystem.*;
import base.WebSocketService;
import frontend.GameWebSocket;

/**
 * Created by alexey on 26.12.2014.
 */
public class MsgAddUser extends MsgToWebSocketService {
    final private GameWebSocket gameWebSocket;
    final private String extra;

    public MsgAddUser(Address from, Address to, GameWebSocket gameWebSocket, String extra) {
        super(from, to);
        this.gameWebSocket = gameWebSocket;
        this.extra = extra;
    }

    public void exec(WebSocketService webSocketService) {
        webSocketService.addUser(gameWebSocket, extra);
    }
}
