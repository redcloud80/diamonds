package it.diamonds.tests;


import static it.diamonds.tests.helper.ComponentHelperForTest.MEDIUM_SLEEP;
import static it.diamonds.tests.helper.ComponentHelperForTest.SHORT_SLEEP;
import static it.diamonds.tests.helper.ComponentHelperForTest.joinAndFailIfNotAlive;
import static it.diamonds.tests.helper.ComponentHelperForTest.runAndSleep;
import it.diamonds.Game;
import it.diamonds.GameLoop;
import it.diamonds.MenuLoop;
import it.diamonds.engine.input.Event;
import it.diamonds.engine.input.Event.Code;
import it.diamonds.engine.input.Event.State;


public class TestGame extends GameTestCase
{
    @Override
    protected void setUp()
    {
        super.setUp();
        game = Game.createGame(environment);
    }


    public void testGameCreatedWithMenuLoop()
    {
        assertTrue(game.getCurrentLoop() instanceof MenuLoop);
    }


    public void testLoopHaveNoNextLoop()
    {
        thread = runAndSleep(getRunnableStart(), MEDIUM_SLEEP);
        assertNull(game.getCurrentLoop().getNextLoop());
    }


    public void testLoopNotFinished()
    {
        thread = runAndSleep(getRunnableStart(), MEDIUM_SLEEP);
        assertNull(game.getCurrentLoop().getNextLoop());
    }


    public void testMusicNotStartd()
    {
        thread = runAndSleep(getRunnableStart(), MEDIUM_SLEEP);

        assertFalse(environment.getAudio().isMusicPlaying());
    }


    public void testEnterKeySetGameLoop()
    {
        thread = runAndSleep(getRunnableStart(), SHORT_SLEEP);

        environment.getKeyboard().notify(Event.create(Code.KEY_ENTER, State.RELEASED));

        joinAndFailIfNotAlive(thread, MEDIUM_SLEEP);

        assertTrue(game.getCurrentLoop() instanceof GameLoop);
    }


    public void testGameNotStartedBeforeEnterKeyPressed()
    {
        game.loopStep();

        environment.getTimer().advance(1000);

        environment.getKeyboard().notify(Event.create(Code.KEY_ENTER, State.RELEASED));

        game.loopStep();

        environment.getKeyboard().notify(Event.create(Code.KEY_LEFT, State.PRESSED));

        game.loopStep();

        GameLoop gameLoop = (GameLoop)game.getCurrentLoop();

        assertEquals(gameLoop.getPlayFieldTwo().getGridController().getGemsPair().getPivot().getRegion().getLeftColumn(), 4);
    }
}
