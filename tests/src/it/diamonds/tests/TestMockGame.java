package it.diamonds.tests;


import static it.diamonds.tests.helper.ComponentHelperForTest.MEDIUM_SLEEP;
import static it.diamonds.tests.helper.ComponentHelperForTest.SHORT_SLEEP;
import static it.diamonds.tests.helper.ComponentHelperForTest.joinAndFailIfNotAlive;
import static it.diamonds.tests.helper.ComponentHelperForTest.joinAndFailIfStillAlive;
import static it.diamonds.tests.helper.ComponentHelperForTest.runAndSleep;
import it.diamonds.Game;
import it.diamonds.tests.mocks.MockLoop;


public class TestMockGame extends GameTestCase
{
    private MockLoop loop;


    @Override
    protected void setUp()
    {
        super.setUp();
        loop = new MockLoop(environment);
        game = new Game(loop, environment);
    }


    public void testGameCreatedWithNullEnvironment()
    {
        try
        {
            Game.createGame(null);
        }
        catch (IllegalArgumentException e)
        {
            return;
        }
        fail("must throw IllegalArgumentException");
    }


    public void testShutDownEngineStopGame()
    {
        thread = runAndSleep(getRunnableStart(), SHORT_SLEEP);
        joinAndFailIfNotAlive(thread, MEDIUM_SLEEP);

        environment.getEngine().shutDown();

        joinAndFailIfStillAlive(thread, MEDIUM_SLEEP);
    }


    public void testLoopHaveNoNextLoop()
    {
        thread = runAndSleep(getRunnableStart(), MEDIUM_SLEEP);
        assertNull(loop.getNextLoop());
    }


    public void testLoopNotFinished()
    {
        thread = runAndSleep(getRunnableStart(), MEDIUM_SLEEP);
        assertNull(loop.getNextLoop());
    }


    public void testExitCurrentLoopWithNoNextLoopCloseGame()
    {
        thread = runAndSleep(getRunnableStart(), SHORT_SLEEP);

        game.getCurrentLoop().setNextLoop(null);
        game.getCurrentLoop().exitLoop();

        joinAndFailIfStillAlive(thread, MEDIUM_SLEEP);
    }


    public void testCloseGameShutDownEnvironment()
    {
        environment.getAudio().playMusic();

        thread = runAndSleep(getRunnableStart(), SHORT_SLEEP);

        game.getCurrentLoop().setNextLoop(null);
        game.getCurrentLoop().exitLoop();

        joinAndFailIfStillAlive(thread, MEDIUM_SLEEP);

        assertTrue(environment.getEngine().isWindowClosed());
        assertFalse(mockKeyboard.isCreated());
        assertFalse(environment.getAudio().isCreated());
        assertFalse(environment.getAudio().isMusicPlaying());
    }


    public void testExitCurrentLoopPassTonextLoop()
    {
        thread = runAndSleep(getRunnableStart(), SHORT_SLEEP);

        MockLoop mockLoop = new MockLoop(environment);
        loop.setNextLoop(mockLoop);
        loop.exitLoop();

        joinAndFailIfNotAlive(thread, MEDIUM_SLEEP);

        assertEquals(mockLoop, game.getCurrentLoop());
    }


    public void testLoopZeroTimeWithCloseWindow()
    {
        long timestamp = environment.getTimer().getTime();
        environment.getEngine().shutDown();

        thread = runAndSleep(getRunnableStart(), SHORT_SLEEP);

        joinAndFailIfStillAlive(thread, MEDIUM_SLEEP);

        assertEquals(timestamp, environment.getTimer().getTime());
        assertEquals(0, loop.getNumStateUpdated());
    }


    public void testLoopOneTimeWithLoopFinished()
    {
        long timestamp = environment.getTimer().getTime();

        loop.exitLoop();

        thread = runAndSleep(getRunnableStart(), SHORT_SLEEP);

        joinAndFailIfStillAlive(thread, MEDIUM_SLEEP);

        assertEquals(timestamp + 1, environment.getTimer().getTime());
        assertEquals(1, loop.getNumStateUpdated());
    }


    public void testExitWithCloseWindow()
    {
        thread = runAndSleep(getRunnableStart(), SHORT_SLEEP);

        environment.getEngine().shutDown();

        joinAndFailIfStillAlive(thread, MEDIUM_SLEEP);
    }


    public void testLoopExitOnExitLoop()
    {
        long timestamp = environment.getTimer().getTime();

        thread = runAndSleep(getRunnableStart(), 100);

        environment.getEngine().shutDown();

        joinAndFailIfStillAlive(thread, SHORT_SLEEP);

        assertTrue(timestamp + 2 < environment.getTimer().getTime());
        long numberOfAdvanceTime = environment.getTimer().getTime() - timestamp;
        assertEquals("The number of advance in timer must be equal to number of updateState in loop", numberOfAdvanceTime, loop.getNumStateUpdated());
    }


    public void testloopStep()
    {
        game.loopStep();
        assertEquals(loop, game.getCurrentLoop());
    }


    public void testloopStepDoOneStepOnCurrentLoop()
    {
        game.loopStep();
        assertEquals(1, loop.getNumStateUpdated());
    }


    public void testLoopStepWithCurrentLoopFinishedAndNextLoopNull()
    {
        loop.exitLoop();
        game.loopStep();
        assertNull(game.getCurrentLoop());
    }


    public void testLoopStepWithCurrentLoopFinishedAndNextLoopNotNull()
    {
        loop.exitLoop();
        loop.setNextLoop(loop);
        game.loopStep();
        assertEquals(loop, game.getCurrentLoop());
    }


    public void testThrowNullPointerExceptionIfLoopStepWithNullCurrentLoop()
    {
        try
        {
            loop.exitLoop();
            game.loopStep();
            game.loopStep();
        }
        catch (Exception e)
        {
            return;
        }
        fail("Must throw NullPointerException");
    }

}
