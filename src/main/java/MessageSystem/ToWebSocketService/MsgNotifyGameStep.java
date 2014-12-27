package MessageSystem.ToWebSocketService;

import MessageSystem.*;
import base.WebSocketService;
import mechanics.GameUser;

/**
 * Created by alexey on 26.12.2014.
 */
public class MsgNotifyGameStep extends MsgToWebSocketService {
    final private boolean correct;
    final private String message;
    final private GameUser user;
    final private boolean fromJoystick;

    public MsgNotifyGameStep(Address from, Address to, boolean correct, String message,
                             GameUser user, boolean fromJoystick) {
        super(from, to);
        this.correct = correct;
        this.message = message;
        this.user = user;
        this.fromJoystick = fromJoystick;
    }

    public void exec(WebSocketService webSocketService) {
        webSocketService.notifyGameStep(correct, message, user, fromJoystick);
    }
}
