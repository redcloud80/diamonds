package it.diamonds.network.input;

import it.diamonds.engine.Timer;
import it.diamonds.engine.input.Event;
import it.diamonds.engine.input.Input;
import it.diamonds.engine.input.InputDevice;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.network.GameConnection;

public final class NetworkInput extends Input
{
    private GameConnection gameConnection;
    
    private NetworkInput(GameConnection gameConnection, InputDevice device, Timer timer)
    {
        super(device, timer);
        this.gameConnection = gameConnection;
    }
    
    
    public static NetworkInput create(GameConnection gameConnection, InputDevice input, Timer timer)
    {
        return new NetworkInput(gameConnection, input, timer);
    }
    
    
    @Override
    public void notify(Event event)
    {
        super.notify(event);
        
        if (event.getCode() != Code.UNKNOWN)
        {            
            gameConnection.write(event.encode());
        }
    }
}
