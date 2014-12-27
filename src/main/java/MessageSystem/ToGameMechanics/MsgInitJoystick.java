package MessageSystem.ToGameMechanics;

import MessageSystem.*;
import base.GameMechanics;

/**
 * Created by alexey on 26.12.2014.
 */
public class MsgInitJoystick extends MsgToGameMechanics {
    final private String username;

    public MsgInitJoystick(Address from, Address to, String username) {
        super(from, to);
        this.username = username;
    }

    public void exec(GameMechanics gameMechanics) {
        gameMechanics.initJoystick(username);
    }
}
