package it.diamonds.engine.input;

import it.diamonds.engine.Environment;

public class InputFactory
{
    protected Environment environment;
    
    
    public InputFactory(Environment environment)
    {
        this.environment = environment;
    }
    
    
    public Input createInputForPlayerOne()
    {
        Input playerOneInput = Input.create(environment.getKeyboard(), environment.getTimer());
        EventMappings mappings = EventMappings.createForPlayerOne(environment.getConfig());
        
        playerOneInput.setEventMappings(mappings);
        
        return playerOneInput;
    }
    
    
    public Input createInputForPlayerTwo()
    {
        Input playerTwoInput = Input.create(environment.getKeyboard(), environment.getTimer());
        EventMappings mappings = EventMappings.createForPlayerTwo(environment.getConfig());
        
        playerTwoInput.setEventMappings(mappings);
        
        return playerTwoInput;
    }
}
