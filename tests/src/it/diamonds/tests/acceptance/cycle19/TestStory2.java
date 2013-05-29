package it.diamonds.tests.acceptance.cycle19;

import it.diamonds.GameLoop;
import it.diamonds.engine.Environment;
import it.diamonds.menu.MenuActionNetPlay;
import it.diamonds.tests.mocks.MockEnvironment;
import it.diamonds.tests.mocks.MockLoop;
import junit.framework.TestCase;


public class TestStory2 extends TestCase
{
    private Environment environment = MockEnvironment.create();
    
    public void testRemoteIpAndPortAreSetCorrectly()
    {
        assertEquals(environment.getConfig().getString("RemoteIp"),"127.0.0.1");
        assertEquals(environment.getConfig().getInteger("RemotePort"), 13371);
    }
    
    
    public void testClientAndServerConnect()
    {
        MenuActionNetPlayThread server = new MenuActionNetPlayThread();

        Thread serverThread = new Thread(server);

        serverThread.start();

        while (server.getConnection() == null
            || !server.getConnection().isServer())
        {
        }

        MenuActionNetPlay client = new MenuActionNetPlay();

        client.execute(new MockLoop(environment), environment);

        assertTrue(client.getConnection().isClient());
    }
    
    
    public void testServerTimeoutIsSetCorrectly()
    {
        MenuActionNetPlay actionNetPlay = new MenuActionNetPlay();

        actionNetPlay.execute(new MockLoop(environment), environment);

        assertEquals(environment.getConfig().getInteger("ServerTimeout"), actionNetPlay.getConnection().getTimeout());
    }


    public void testGameLoopStartsAfterMenuActionNetPlay()
    {
        MenuActionNetPlay actionNetPlay = new MenuActionNetPlay();
        MockLoop mockLoop = new MockLoop(environment);

        actionNetPlay.execute(mockLoop, environment);

        assertTrue(mockLoop.getNextLoop() instanceof GameLoop);
    }
    
    
    private class MenuActionNetPlayThread extends MenuActionNetPlay implements Runnable
    {
        public void run()
        {
            execute(new MockLoop(environment), environment);
        }
    }
}