package MessageSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by alexey on 26.12.2014.
 */
public class MessageSystem {
    private AddressService addressService;
    private Map<Address, ConcurrentLinkedQueue<Msg>> messages = new HashMap<>();
    private volatile static MessageSystem messageSystem;

    private MessageSystem() {}

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    public void addService(Abonent abonent) {
        messages.put(abonent.getAddress(), new ConcurrentLinkedQueue<>());
    }

    public static MessageSystem instance() {
        if (messageSystem == null) {
            synchronized (MessageSystem.class) {
                MessageSystem tempMessageSystem = messageSystem;
                if (tempMessageSystem == null)
                    messageSystem = new MessageSystem();
            }
        }
        return messageSystem;
    }

    public AddressService getAddressService() {
        return addressService;
    }

    public void sendMessage(Msg message) {
        Queue<Msg> messageQueue = messages.get(message.getTo());
        messageQueue.add(message);
    }

    public void execForAbonent(Abonent abonent) {
        Queue<Msg> messageQueue = messages.get(abonent.getAddress());
        while (!messageQueue.isEmpty()) {
            Msg message = messageQueue.poll();
            message.exec(abonent);
        }
    }
}
