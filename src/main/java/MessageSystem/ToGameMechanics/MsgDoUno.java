package MessageSystem.ToGameMechanics;

import MessageSystem.*;
import base.GameMechanics;

/**
 * Created by alexey on 26.12.2014.
 */
public class MsgDoUno extends MsgToGameMechanics {
    private String username;

    public MsgDoUno(Address from, Address to, String username) {
        super(from, to);
        this.username = username;
    }

    void exec(GameMechanics gameMechanics) {
        gameMechanics.doUno(username);
    }
}
