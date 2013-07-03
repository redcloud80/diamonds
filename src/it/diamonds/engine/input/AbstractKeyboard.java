package it.diamonds.engine.input;


import it.diamonds.engine.input.Event.State;

import java.util.HashMap;
import java.util.LinkedList;


public abstract class AbstractKeyboard implements Keyboard
{
    private HashMap<Boolean, State> stateMappings;

    private LinkedList<Listener> listeners;


    protected AbstractKeyboard()
    {
        listeners = new LinkedList<Listener>();

        stateMappings = new HashMap<Boolean, State>();

        fillStates();
    }


    public final void setListener(Listener listener)
    {
        listeners.add(listener);
    }


    public final void notify(Event event)
    {

        for (Listener listener : new LinkedList<Listener>(listeners))
        {
            listener.notify(event);
        }
    }


    protected boolean isListenerRegistred(Listener listener)
    {
        return listeners.contains(listener);
    }


    protected State getState(boolean eventState)
    {
        return stateMappings.get(eventState);
    }


    private void fillStates()
    {
        stateMappings.put(false, State.RELEASED);
        stateMappings.put(true, State.PRESSED);
    }
}
