package MessageSystem.ToWebSocketService;

import MessageSystem.Address;
import base.WebSocketService;
import mechanics.GameUser;

/**
 * Created by alexey on 26.12.2014.
 */
public class MsgNotifyChangeFocus extends MsgToWebSocketService {
    final private GameUser user;

    public MsgNotifyChangeFocus(Address from, Address to, GameUser user) {
        super(from, to);
        this.user = user;
    }

    public void exec(WebSocketService webSocketService) {
        webSocketService.notifyChangeFocus(user);
    }
}