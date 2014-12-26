package MessageSystem;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by alexey on 26.12.2014.
 */
public class Address {
    static private AtomicInteger abonentIdCreator = new AtomicInteger();
    final private int abonentId;

    public Address() {
        this.abonentId = abonentIdCreator.incrementAndGet();
    }

    public int hashCode() {
        return abonentId;
    }
}
