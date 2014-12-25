package testresources;

import org.junit.*;
import resources.ResourceSystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
/**
 * Created by К on 23.12.2014.
 */
public class TestResourceSystem {
    public ResourceSystem testRS = ResourceSystem.instance();

    @Test
    public void testResourceSystem() {
        assertNotNull("Testing server config resource", testRS.getServerConfigResource());
        assertNotNull("Testing cards resource", testRS.getCardsResource());
        assertNotNull("Testing game parameters resource", testRS.getGameParamsResource());
        assertNotNull("Testing database config resource", testRS.getDBConfigResource());
    }
}
