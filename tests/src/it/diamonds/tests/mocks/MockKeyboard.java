package it.diamonds.tests.mocks;


import it.diamonds.engine.input.AbstractKeyboard;
import it.diamonds.engine.input.Listener;
import it.diamonds.engine.input.Event.State;


public final class MockKeyboard extends AbstractKeyboard
{
    private boolean created = false;

    private boolean updated = false;
    
    private int updatesNumber = 0;


    private MockKeyboard()
    {
        created = true;
    }


    public static MockKeyboard create()
    {
        return new MockKeyboard();
    }


    public boolean isCreated()
    {
        return created;
    }


    public void update()
    {
        updated = true;

        updatesNumber++;
    }


    public void shutDown()
    {
        created = false;
    }


    public boolean updated()
    {
        return updated;
    }
    
    
    public int getUpdatesNumber()
    {
        return updatesNumber;
    }


    public boolean isListenerRegistred(Listener listener)
    {
        return super.isListenerRegistred(listener);
    }


    public State getState(boolean state)
    {
        return super.getState(state);
    }
}
