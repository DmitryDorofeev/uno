package MessageSystem.ToWebSocketService;

import MessageSystem.*;
import base.WebSocketService;

/**
 * Created by alexey on 26.12.2014.
 */
public abstract class MsgToWebSocketService extends Msg {

    public MsgToWebSocketService(Address from, Address to) {
        super(from, to);
    }

    public void exec(Abonent abonent) {
        if (abonent instanceof WebSocketService)
            exec((WebSocketService)abonent);
    }

    public abstract void exec(WebSocketService webSocketService);
}
