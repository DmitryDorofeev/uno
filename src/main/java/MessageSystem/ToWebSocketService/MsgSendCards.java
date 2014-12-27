package MessageSystem.ToWebSocketService;

import MessageSystem.*;
import base.WebSocketService;
import mechanics.GameUser;

/**
 * Created by alexey on 26.12.2014.
 */
public class MsgSendCards extends MsgToWebSocketService {
    final private GameUser user;

    public MsgSendCards(Address from, Address to, GameUser user) {
        super(from, to);
        this.user = user;
    }

    public void exec(WebSocketService webSocketService) {
        webSocketService.sendCards(user);
    }
}
