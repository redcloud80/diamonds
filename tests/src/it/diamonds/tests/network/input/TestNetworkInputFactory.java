package it.diamonds.tests.network.input;

import it.diamonds.engine.Config;
import it.diamonds.engine.Environment;
import it.diamonds.engine.input.EventMappings;
import it.diamonds.engine.input.Input;
import it.diamonds.engine.input.InputFactory;
import it.diamonds.network.GameConnection;
import it.diamonds.network.input.NetworkInput;
import it.diamonds.network.input.NetworkInputDevice;
import it.diamonds.network.input.NetworkInputFactory;
import it.diamonds.tests.mocks.MockEnvironment;
import it.diamonds.tests.network.MockSocketFactory;
import junit.framework.TestCase;

public class TestNetworkInputFactory extends TestCase
{
    private Config config;
    private InputFactory inputFactory;
    
    @Override
    protected void setUp() throws Exception
    {
        Environment environment = MockEnvironment.create();
        GameConnection gameConnection = new GameConnection(environment, new MockSocketFactory());

        inputFactory = new NetworkInputFactory(environment, gameConnection);
        config = environment.getConfig();
    }
    
    
    public void testCreateInputForPlayerOne()
    {
        assertTrue(inputFactory.createInputForPlayerOne() instanceof NetworkInput);
    }
    
    
    public void testCreateInputForPlayerTwo()
    {
        assertTrue(inputFactory.createInputForPlayerOne() instanceof Input);
    }
    
    
    public void testPlayerTwoUsesNetworkInputDevice()
    {
        Input input = inputFactory.createInputForPlayerTwo();
        
        assertTrue(input.getInputDevice() instanceof NetworkInputDevice);
    }
    
    
    public void testPlayerOneUsesPlayerOneEventMappings()
    {             
        Input input = inputFactory.createInputForPlayerOne();
        EventMappings mappings = input.getEventMappings();

        assertEquals(EventMappings.createForPlayerOne(config), mappings);
    }
    
    
    public void testPlayerTwoUsesPlayerOneEventMappings()
    { 
        Input input = inputFactory.createInputForPlayerTwo();
        EventMappings mappings = input.getEventMappings();

        assertEquals(EventMappings.createForPlayerOne(config), mappings);
    }    
}
