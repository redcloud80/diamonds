package it.diamonds.tests.mocks;


import it.diamonds.engine.input.AbstractEventHandler;
import it.diamonds.engine.input.InputReactor;


public class MockEventHandler extends AbstractEventHandler
{
    private boolean executed;

    private boolean pressed;


    public static MockEventHandler create()
    {
        return new MockEventHandler();
    }


    protected void executeWhenPressed(InputReactor inputReactor)
    {
        executed = true;
        pressed = true;
    }


    protected void executeWhenReleased(InputReactor inputReactor)
    {
        executed = true;
        pressed = false;
    }


    // CheckStyle
    public boolean executed()
    {
        return executed;
    }


    public boolean pressed()
    {
        return pressed;
    }


    public void resetStatus()
    {
        ;
    }

}
