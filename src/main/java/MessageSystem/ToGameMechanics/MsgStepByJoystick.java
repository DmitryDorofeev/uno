package MessageSystem.ToGameMechanics;

import MessageSystem.*;
import base.GameMechanics;

/**
 * Created by alexey on 26.12.2014.
 */
public class MsgStepByJoystick extends MsgToGameMechanics {
    final private String username;
    final private String message;
    final private String newColor;
    final private Long focusOnCard;

    public MsgStepByJoystick(Address from, Address to, String username, String message, String newColor, Long focusOnCard) {
        super(from, to);
        this.username = username;
        this.message = message;
        this.newColor = newColor;
        this.focusOnCard = focusOnCard;
    }

    void exec(GameMechanics gameMechanics) {
        gameMechanics.stepByJoystick(username, message, newColor, focusOnCard);
    }
}
