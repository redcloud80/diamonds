package it.diamonds.tests.network.input;

import it.diamonds.engine.Environment;
import it.diamonds.engine.input.Input;
import it.diamonds.engine.input.InputFactory;
import it.diamonds.network.GameConnection;
import it.diamonds.network.input.NetworkInput;

public class MockNetworkInputFactory extends InputFactory
{
    private GameConnection gameConnection;

    
    public MockNetworkInputFactory(Environment environment, GameConnection gameConnection)
    {
        super(environment);
        this.gameConnection = gameConnection;
    }
    
    
    public Input createInputForPlayerOne()
    {
        return NetworkInput.create(gameConnection, environment.getKeyboard(), environment.getTimer());
    }
    
    
    public Input createInputForPlayerTwo()
    {
        return Input.create(new MockNetworkInputDevice(gameConnection), environment.getTimer());
    }
}
