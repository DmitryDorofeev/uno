package MessageSystem.ToWebSocketService;

import MessageSystem.Address;
import base.WebSocketService;
import mechanics.GameUser;

/**
 * Created by alexey on 26.12.2014.
 */
public class MsgSendScores extends MsgToWebSocketService {
    private GameUser user;

    public MsgSendScores(Address from, Address to, GameUser user) {
        super(from, to);
        this.user = user;
    }

    public void exec(WebSocketService webSocketService) {
        webSocketService.sendScores(user);
    }
}