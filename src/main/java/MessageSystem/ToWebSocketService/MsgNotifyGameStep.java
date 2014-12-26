package MessageSystem.ToWebSocketService;

import MessageSystem.*;
import base.WebSocketService;
import mechanics.GameUser;

/**
 * Created by alexey on 26.12.2014.
 */
public class MsgNotifyGameStep extends MsgToWebSocketService {
    private boolean correct;
    private String message;
    private GameUser user;

    public MsgNotifyGameStep(Address from, Address to, boolean correct, String message, GameUser user) {
        super(from, to);
        this.correct = correct;
        this.message = message;
        this.user = user;
    }

    public void exec(WebSocketService webSocketService) {
        webSocketService.notifyGameStep(correct, message, user);
    }
}
