package MessageSystem;

/**
 * Created by alexey on 26.12.2014.
 */
public class AddressService {
    private Address gameMechanics;
    private Address webSocketService;

    public void setGameMechanics(Address gameMechanics) {
        this.gameMechanics = gameMechanics;
    }

    public void setWebSocketService (Address webSocketService) {
        this.webSocketService = webSocketService;
    }

    public Address getGameMechanics() {
        return gameMechanics;
    }

    public Address getWebSocketService() {
        return webSocketService;
    }
}
