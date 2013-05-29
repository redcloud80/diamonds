package it.diamonds.tests.network.input;

import it.diamonds.engine.input.InputDevice;
import it.diamonds.engine.input.Listener;
import it.diamonds.network.GameConnection;
import it.diamonds.network.input.NetworkInputDevice;

public class MockNetworkInputDevice extends NetworkInputDevice implements InputDevice
{
    private Listener listener;
    
    private boolean updated = false;
    
    private int updatesNumber = 0;
    
    
    public MockNetworkInputDevice(GameConnection gameConnection)
    {
        super(gameConnection);
    }
    
    
    public void setListener(Listener listener)
    {
        this.listener = listener;
    }
    
    
    public void removeListener(Listener listener)
    {
        this.listener = null;
    }
    
    
    public Listener getListener()
    {
        return this.listener;
    }
    
    
    public void update()
    {
        updated = true;

        updatesNumber++;
    }
    
    
    public boolean updated()
    {
        return updated;
    }
    
    
    public int getUpdatesNumber()
    {
        return updatesNumber;
    }
}
