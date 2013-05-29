package it.diamonds.tests.engine.input;

import it.diamonds.engine.Environment;
import it.diamonds.engine.input.Input;
import it.diamonds.engine.input.InputFactory;
import it.diamonds.tests.mocks.MockKeyboard;

public class MockInputFactory extends InputFactory
{
    private MockKeyboard mockKeyboard;
    
    public MockInputFactory(Environment environment)
    {
        super(environment);
        this.mockKeyboard = MockKeyboard.create();
    }
    
    
    public Input createInputForPlayerOne()
    {
        return Input.create(mockKeyboard, environment.getTimer());
    }
    
    
    public Input createInputForPlayerTwo()
    {
        return Input.create(mockKeyboard, environment.getTimer());
    }
}
