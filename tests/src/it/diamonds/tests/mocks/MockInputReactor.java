package it.diamonds.tests.mocks;


import static it.diamonds.tests.helper.ComponentHelperForTest.createEventMappings;
import it.diamonds.engine.Environment;
import it.diamonds.engine.input.AbstractEventHandler;
import it.diamonds.engine.input.EventHandler;
import it.diamonds.engine.input.Input;
import it.diamonds.engine.input.InputReactor;
import it.diamonds.engine.input.InputReactorInterface;
import it.diamonds.engine.input.Event.Code;


public class MockInputReactor implements InputReactorInterface
{
    private boolean reacted = false;
    
    
    public static InputReactor create(Environment environment) 
    {
       Input input = Input.create(environment.getKeyboard(), environment.getTimer());
       input.setEventMappings(createEventMappings());
       
       return new InputReactor(input, environment.getConfig().getInteger("NormalRepeatDelay"), environment.getConfig().getInteger("FastRepeatDelay"));
    }
    
    
    public void addHandler(Code code, AbstractEventHandler handler)
    {
        ;
    }


    public EventHandler getEventHandler(Code code)
    {
        return null;
    }


    public long getNormalRepeatDelay()
    {
        return 0;
    }


    public long getFastRepeatDelay()
    {
        return 0;
    }


    public long getLastInputTimeStamp()
    {
        return 0;
    }


    public void reactToInput(long timer)
    {
        reacted = true;
    }


    public boolean hasReacted()
    {
        return reacted;
    }


    public void emptyQueue()
    {
        ;
    }
}
