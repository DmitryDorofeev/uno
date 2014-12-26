package MessageSystem.ToGameMechanics;

import MessageSystem.*;
import base.GameMechanics;

/**
 * Created by alexey on 26.12.2014.
 */
public class MsgGameStep extends MsgToGameMechanics {
    private String username;
    private Long focusOnCard;
    private String newColor;

    public MsgGameStep(Address from, Address to, String username, Long focusOnCard, String newColor) {
        super(from, to);
        this.username = username;
        this.focusOnCard = focusOnCard;
        this.newColor = newColor;
    }

    public void exec(GameMechanics gameMechanics) {
        gameMechanics.gameStep(username, focusOnCard, newColor);
    }
}
