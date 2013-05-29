package it.diamonds.network.input;

import it.diamonds.engine.input.Event;
import it.diamonds.engine.input.InputDevice;
import it.diamonds.engine.input.Listener;
import it.diamonds.network.GameConnection;
import it.diamonds.network.TCPSocket;

public class NetworkInputDevice implements InputDevice
{
    private GameConnection gameConnection;
    private Listener listener;
    
    
    public NetworkInputDevice(GameConnection gameConnection)
    {
        this.gameConnection = gameConnection;
    }
    
    
    public void setListener(Listener listener)
    {
        this.listener = listener;
    }
    
    
    public void update()
    {
        int data = gameConnection.read();
        
        while(data != TCPSocket.READ_ERROR)
        {
            notify(Event.create((byte) data));
            data = gameConnection.read();
        }
    }
    
    
    public void notify(Event event)
    {
        listener.notify(event);
    }
    
    
    public void removeListener(Listener listener)
    {
        this.listener = null;
    }

}
