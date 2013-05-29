package it.diamonds.tests.acceptance.cycle20;


import it.diamonds.engine.Environment;
import it.diamonds.network.GameConnection;
import it.diamonds.network.SocketFactory;
import it.diamonds.tests.mocks.MockEnvironment;
import junit.framework.TestCase;

public class TestStory1 extends TestCase
{
    private Environment environment = MockEnvironment.create();
    
    public void testPacketsAreSentImmediately()
    {
        GameConnection player1 = new GameConnection(environment, new SocketFactory());
        GameConnectionThread player2 = new GameConnectionThread(environment, new SocketFactory());
        
        Thread player2Thread = new Thread(player2);
        player2Thread.start();
        
        while(!player2.isServer())
        {
            Thread.yield();
        }
        
        player1.connect();
        
        while(!player2.isConnected())
        {
            Thread.yield();
        }    
        
        int sentData = 8;
        
        player1.write(sentData);
        int receivedData = player2.read();
        
        assertEquals(sentData, receivedData);
    }
    
    
    private class GameConnectionThread extends GameConnection implements Runnable
    {
        public GameConnectionThread(Environment environment, SocketFactory socketFactory)
        {
            super(environment, socketFactory);
        }
        
        public void run()
        {
            connect();
        }
    }
}
