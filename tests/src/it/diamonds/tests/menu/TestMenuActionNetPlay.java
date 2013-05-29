package it.diamonds.tests.menu;


import it.diamonds.AbstractLoop;
import it.diamonds.GameLoop;
import it.diamonds.menu.MenuActionNetPlay;
import it.diamonds.network.input.NetworkInput;
import it.diamonds.tests.EnvironmentTestCase;
import it.diamonds.tests.mocks.MockLoop;
import it.diamonds.tests.network.MockSocketFactory;


public class TestMenuActionNetPlay extends EnvironmentTestCase
{
    private MenuActionNetPlay actionNetPlay;

    private AbstractLoop loop;


    @Override
    protected void setUp()
    {
        super.setUp();
        loop = new MockLoop(environment);
        actionNetPlay = new MenuActionNetPlay(new MockSocketFactory());
    }


    public void testConnectionCreated()
    {
        actionNetPlay.execute(loop, environment);
        assertNotNull(actionNetPlay.getConnection());
    }
    
    public void testConnection()
    {
        actionNetPlay.execute(loop, environment);
        assertTrue(actionNetPlay.getConnection().isConnected());
    }

    public void testLoopIsFinished()
    {
        actionNetPlay.execute(loop, environment);
        assertTrue(loop.isFinished());
    }


    public void testGameLoopIsNextLoop()
    {
        actionNetPlay.execute(loop, environment);
        assertTrue(loop.getNextLoop() instanceof GameLoop);
    }
    
    
    public void testPlayerOneInput()
    {
        actionNetPlay.execute(loop, environment);
        
        GameLoop gameLoop = (GameLoop)loop.getNextLoop();
        
        assertTrue(gameLoop.getPlayerOneInput() instanceof NetworkInput);
    }
}
