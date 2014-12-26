package MessageSystem.ToGameMechanics;

import MessageSystem.*;
import base.GameMechanics;

/**
 * Created by alexey on 26.12.2014.
 */
public class MsgStepByJoystick extends MsgToGameMechanics {
    private String username;
    private String message;
    private String newColor;

    public MsgStepByJoystick(Address from, Address to, String username, String message, String newColor) {
        super(from, to);
        this.username = username;
        this.message = message;
        this.newColor = newColor;
    }

    void exec(GameMechanics gameMechanics) {
        gameMechanics.stepByJoystick(username, message, newColor);
    }
}
