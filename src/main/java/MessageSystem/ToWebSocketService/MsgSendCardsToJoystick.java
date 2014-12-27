package MessageSystem.ToWebSocketService;

import MessageSystem.Address;
import base.WebSocketService;
import mechanics.GameUser;
import resources.CardResource;

import java.util.List;

/**
 * Created by alexey on 26.12.2014.
 */
public class MsgSendCardsToJoystick extends MsgToWebSocketService {
    final private boolean correct;
    final private String message;
    final private String username;
    final private long focusOnCard;
    final private List<CardResource> cards;

    public MsgSendCardsToJoystick(Address from, Address to, boolean correct, String message, String username,
                                  long focusOnCard, List<CardResource> cards) {
        super(from, to);
        this.correct = correct;
        this.message = message;
        this.username = username;
        this.focusOnCard = focusOnCard;
        this.cards = cards;
    }

    public void exec(WebSocketService webSocketService) {
        webSocketService.sendCardsToJoystick(correct, message, username, focusOnCard, cards);
    }
}