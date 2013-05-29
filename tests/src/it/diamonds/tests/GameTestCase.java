package it.diamonds.tests;


import static it.diamonds.tests.helper.ComponentHelperForTest.MEDIUM_SLEEP;
import static it.diamonds.tests.helper.ComponentHelperForTest.joinAndFailIfStillAlive;
import it.diamonds.AbstractLoop;
import it.diamonds.Game;
import it.diamonds.tests.mocks.MockTimer;


public abstract class GameTestCase extends EnvironmentTestCase
{
    protected Game game;

    protected Thread thread;


    @Override
    protected void tearDown() throws Exception
    {
        // TODO the comment are for build testing
        if (thread != null && thread.isAlive())
        {
            // synchronized (game)
            // {
            AbstractLoop currentLoop = game.getCurrentLoop();
            if (currentLoop != null)
            {
                // synchronized (currentLoop)
                // {
                currentLoop.setNextLoop(null);
                currentLoop.exitLoop();
                // }
            }
            joinAndFailIfStillAlive(thread, MEDIUM_SLEEP * 10);
            // }
        }

        super.tearDown();
    }


    protected Runnable getRunnableStart()
    {
        ((MockTimer)environment.getTimer()).sleepOnAdvance(true);
        return new Runnable()
        {
            public void run()
            {
                game.loop();
            }
        };
    }

}
