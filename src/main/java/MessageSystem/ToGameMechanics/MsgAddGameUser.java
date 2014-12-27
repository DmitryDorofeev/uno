package MessageSystem.ToGameMechanics;

import MessageSystem.ToGameMechanics.MsgToGameMechanics;
import base.GameMechanics;

/**
 * Created by alexey on 26.12.2014.
 */
public class MsgAddGameUser extends MsgToGameMechanics {
    final private String username;
    final private Long players;

    public MsgAddGameUser(MessageSystem.Address from, MessageSystem.Address to, String username, Long players) {
        super(from, to);
        this.username = username;
        this.players = players;
    }

    public void exec(GameMechanics gameMechanics) {
        gameMechanics.addUser(username, players);
    }
}
