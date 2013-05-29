package it.diamonds.menu;

import it.diamonds.AbstractLoop;
import it.diamonds.GameLoop;
import it.diamonds.engine.Environment;
import it.diamonds.engine.input.InputFactory;
import it.diamonds.network.GameConnection;
import it.diamonds.network.SocketFactory;
import it.diamonds.network.input.NetworkInputFactory;


public class MenuActionNetPlay implements MenuAction
{
    private GameConnection gameConnection;
    private SocketFactory socketFactory;

    public MenuActionNetPlay()
    {
        this.socketFactory = new SocketFactory();
    }

    public MenuActionNetPlay(SocketFactory socketFactory)
    {
        this.socketFactory = socketFactory;
    }


    public void execute(AbstractLoop loop, Environment environment)
    {
        gameConnection = new GameConnection(environment, socketFactory);
        gameConnection.connect();  
        
        InputFactory inputFactory = new NetworkInputFactory(environment, gameConnection);
        
        loop.setNextLoop(new GameLoop(environment, inputFactory));
        loop.exitLoop();
    }
    

    public GameConnection getConnection()
    {
        return gameConnection;
    }
}
