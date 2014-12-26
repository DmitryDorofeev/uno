package MessageSystem.ToWebSocketService;

import MessageSystem.*;
import base.WebSocketService;
import mechanics.GameUser;

/**
 * Created by alexey on 26.12.2014.
 */
public class MsgNotifyStartGame extends MsgToWebSocketService {
    private GameUser user;

    public MsgNotifyStartGame(Address from, Address to, GameUser user) {
        super(from, to);
        this.user = user;
    }

    public void exec(WebSocketService webSocketService) {
        webSocketService.notifyStartGame(user);
    }
}
