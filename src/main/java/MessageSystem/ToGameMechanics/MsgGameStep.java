package MessageSystem.ToGameMechanics;

import MessageSystem.*;
import base.GameMechanics;

/**
 * Created by alexey on 26.12.2014.
 */
public class MsgGameStep extends MsgToGameMechanics {
    final private String username;
    final private Long focusOnCard;
    final private String newColor;
    final private boolean fromJoystick;

    public MsgGameStep(Address from, Address to, String username, Long focusOnCard, String newColor, boolean fromJoystick) {
        super(from, to);
        this.username = username;
        this.focusOnCard = focusOnCard;
        this.newColor = newColor;
        this.fromJoystick = fromJoystick;
    }

    public void exec(GameMechanics gameMechanics) {
        gameMechanics.gameStep(username, focusOnCard, newColor, fromJoystick);
    }
}
