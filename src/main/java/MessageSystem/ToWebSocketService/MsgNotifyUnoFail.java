package MessageSystem.ToWebSocketService;

import MessageSystem.Address;
import base.WebSocketService;
import mechanics.GameUser;

/**
 * Created by alexey on 26.12.2014.
 */
public class MsgNotifyUnoFail extends MsgToWebSocketService {
    final private String message;
    final private GameUser user;

    public MsgNotifyUnoFail(Address from, Address to, String message, GameUser user) {
        super(from, to);
        this.message = message;
        this.user = user;
    }

    public void exec(WebSocketService webSocketService) {
        webSocketService.notifyUnoFail(message, user);
    }
}