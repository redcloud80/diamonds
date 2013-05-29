package it.diamonds.network.input;

import it.diamonds.engine.Environment;
import it.diamonds.engine.input.EventMappings;
import it.diamonds.engine.input.Input;
import it.diamonds.engine.input.InputFactory;
import it.diamonds.network.GameConnection;

public class NetworkInputFactory extends InputFactory
{
    private GameConnection gameConnection;
    
    
    public NetworkInputFactory(Environment environment, GameConnection gameConnection)
    {
        super(environment);
        this.gameConnection = gameConnection;
    }
    
    
    public Input createInputForPlayerOne()
    {
        Input playerOneInput = NetworkInput.create(gameConnection, environment.getKeyboard(), environment.getTimer());
        EventMappings mappings = EventMappings.createForPlayerOne(environment.getConfig());
        
        playerOneInput.setEventMappings(mappings);
        
        return playerOneInput;
    }
    
    
    public Input createInputForPlayerTwo()
    {
        Input playerTwoInput = Input.create(new NetworkInputDevice(gameConnection), environment.getTimer());
        EventMappings mappings = EventMappings.createForPlayerOne(environment.getConfig());
        
        playerTwoInput.setEventMappings(mappings);
        
        return playerTwoInput;
    }
}
