package MessageSystem.ToGameMechanics;

import MessageSystem.*;
import base.GameMechanics;

/**
 * Created by alexey on 26.12.2014.
 */
public abstract class MsgToGameMechanics extends Msg {

    public MsgToGameMechanics(Address from, Address to) {
        super(from, to);
    }

    public void exec(Abonent abonent) {
        if (abonent instanceof GameMechanics) {
            exec((GameMechanics)abonent);
        }
    }

    abstract void exec(GameMechanics gameMechanics);
}
