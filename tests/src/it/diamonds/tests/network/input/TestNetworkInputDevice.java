package it.diamonds.tests.network.input;

import it.diamonds.engine.Environment;
import it.diamonds.engine.input.Event;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.engine.input.Event.State;
import it.diamonds.network.GameConnection;
import it.diamonds.network.input.NetworkInputDevice;
import it.diamonds.tests.mocks.MockEnvironment;
import it.diamonds.tests.mocks.MockListener;
import it.diamonds.tests.network.MockSocket;
import it.diamonds.tests.network.MockSocketFactory;
import junit.framework.TestCase;

public class TestNetworkInputDevice extends TestCase
{
    private Environment environment;
    private GameConnection gameConnection;
    private NetworkInputDevice inputDevice;
    private MockListener listener;
    
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        
        environment = MockEnvironment.create();
        listener = new MockListener();
        
        gameConnection = new GameConnection(environment, new MockSocketFactory());
        inputDevice = new NetworkInputDevice(gameConnection);
        
        gameConnection.connect();
        inputDevice.setListener(listener);
    }
    
    
    public void testNoEventReceivedBeforeNotify()
    {
        Event event = listener.getLastEvent();        
        assertNull(event);
        
        inputDevice.notify(Event.create(Event.Code.UP, Event.State.PRESSED));
        
        event = listener.getLastEvent();        
        assertNotNull(event);
    }
    

    public void testAllEventsAreReceivedCorrectly()
    {
        for (State state : State.values())
        {
            for (Code code : Code.values())
            {                
                inputDevice.notify(Event.create(code, state));
                
                Event lastEvent = listener.getLastEvent();
                
                assertEquals(code, lastEvent.getCode());                
                assertEquals(state, lastEvent.getState());             
            }   
        }
    }
    
    
    public void testUnknownEventsAreReceivedCorrectly()
    {
        inputDevice.notify(Event.create(Event.Code.UNKNOWN, Event.State.PRESSED));
        
        Event sentEvent = listener.getLastEvent();
        
        assertEquals(Event.Code.UNKNOWN, sentEvent.getCode());                
        assertEquals(Event.State.PRESSED, sentEvent.getState());
    }
    
    
    public void testNoEventReceivedBeforeReadingFromNetwork()
    {
        MockSocket client = (MockSocket)gameConnection.getClient();
        
        inputDevice.update();
        
        Event event = listener.getLastEvent();
        assertNull(event);
        
        client.setLastInputData(Event.create(Event.Code.UP, Event.State.PRESSED).encode());                
        inputDevice.update();
        
        event = listener.getLastEvent();
        assertNotNull(event);
    }
    
    
    public void testAllEventsAreReceivedCorrectlyFromNetwork()
    {        
        MockSocket client = (MockSocket)gameConnection.getClient();        
        
        for (State state : State.values())
        {
            for (Code code : Code.values())
            {
                client.setLastInputData(Event.create(code, state).encode());                
                inputDevice.update();                            
                
                Event newEvent = listener.getLastEvent();
                
                assertEquals(code, newEvent.getCode());                
                assertEquals(state, newEvent.getState());              
            }
        }
    }
    
    
    public void testRemoveListener()
    {
        MockNetworkInputDevice mockInputDevice = new MockNetworkInputDevice(gameConnection);
        
        mockInputDevice.setListener(listener);
        
        assertNotNull(mockInputDevice.getListener());
        
        mockInputDevice.removeListener(listener);
        
        assertNull(mockInputDevice.getListener());
    }
}
