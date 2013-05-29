package it.diamonds.tests.mocks;


import it.diamonds.AbstractLoop;
import it.diamonds.GameLoop;
import it.diamonds.engine.Environment;


public class MockLoop extends AbstractLoop
{
    private boolean eventMappingsCreated;
    
    private Environment environment;

    private boolean inputCreated;

    private int numStateUpdated = 0;


    public MockLoop(Environment env, String[] textureList, String backgroundTexture)
    {
        super(env, textureList, backgroundTexture);
        
        this.environment = env;
        
        createEventMappings();
        
        createInput();      
    }


    public MockLoop(Environment env)
    {
        super(env, new String[0], GameLoop.BACKGROUND);
        
        this.environment = env;
        
        createEventMappings();
        
        createInput();
    }


    protected void createEventMappings()
    {
        eventMappingsCreated = true;
    }


    private void createInput()
    {
        inputCreated = true;
    }
    
    
    protected void processInput()
    {
        environment.getKeyboard().update();
    }


    @Override
    protected void updateState()
    {
        numStateUpdated++;
    }


    public boolean isEventMappingsCreated()
    {
        return eventMappingsCreated;
    }


    public boolean isInputCreated()
    {
        return inputCreated;
    }


    public long getLoopTimestamp()
    {
        return loopTimestamp;
    }


    public long getLastRender()
    {
        return lastRender;
    }


    public boolean isStateUpdated()
    {
        return numStateUpdated > 0;
    }


    public int getNumStateUpdated()
    {
        return numStateUpdated;
    }
}
