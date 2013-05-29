package it.diamonds.tests.mocks;


import it.diamonds.engine.input.Event;
import it.diamonds.engine.input.Listener;


public class MockListener implements Listener
{
    private boolean isNotify = false;

    private Event lastEvent = null;


    public void notify(Event event)
    {
        isNotify = true;
        lastEvent = event;

    }


    public boolean isNotify()
    {
        return isNotify;
    }


    public Event getLastEvent()
    {
        return lastEvent;
    }
}
