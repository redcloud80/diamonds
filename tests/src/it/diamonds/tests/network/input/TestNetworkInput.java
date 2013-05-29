package it.diamonds.tests.network.input;

import static it.diamonds.tests.helper.ComponentHelperForTest.createEventMappings;
import it.diamonds.engine.Environment;
import it.diamonds.engine.input.Event;
import it.diamonds.network.GameConnection;
import it.diamonds.network.input.NetworkInput;
import it.diamonds.tests.mocks.MockEnvironment;
import it.diamonds.tests.network.MockSocket;
import it.diamonds.tests.network.MockSocketFactory;
import junit.framework.TestCase;

public class TestNetworkInput extends TestCase
{
    private NetworkInput networkInput;
    
    private Environment environment;
    
    private GameConnection gameConnection;


    protected void setUp()
    {
        environment = MockEnvironment.create();
        
        gameConnection = new GameConnection(environment, new MockSocketFactory());
        gameConnection.connect();
        
        networkInput = NetworkInput.create(gameConnection, environment.getKeyboard(), environment.getTimer());
        networkInput.setEventMappings(createEventMappings());
        

    }
    
    
    public void testInputWrittenOnGameConnection()
    {
        Event event = Event.create(Event.Code.UP, Event.State.PRESSED);
        MockSocket client = (MockSocket)gameConnection.getClient();
        
        int encodedEvent = event.encode();
        
        networkInput.notify(event);
        
        assertEquals(encodedEvent, client.getLastOutputData()); 
    }
    
    
    public void testUnkownInputIsNotWritten()
    {
        Event event = Event.create(Event.Code.UNKNOWN, Event.State.PRESSED);
        MockSocket client = (MockSocket)gameConnection.getClient();
        
        networkInput.notify(event);
        
        assertTrue(client.isOutputBufferEmpty()); 
    }

}
