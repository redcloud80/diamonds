package it.diamonds.tests.grid.state;


import static it.diamonds.droppable.DroppableColor.DIAMOND;
import it.diamonds.ScoreCalculator;
import it.diamonds.engine.Point;
import it.diamonds.engine.input.InputReactor;
import it.diamonds.grid.GridController;
import it.diamonds.grid.state.AbstractControllerState;
import it.diamonds.grid.state.WaitBeforeNewGemsPairState;
import it.diamonds.tests.GridTestCase;
import it.diamonds.tests.mocks.MockInputReactor;
import it.diamonds.tests.mocks.MockRandomGenerator;
import it.diamonds.tests.mocks.MockTimer;


public class TestWaitStateBeforeNewGemsPair extends GridTestCase
{
    private AbstractControllerState state;


    public void setUp()
    {
        super.setUp();

        int[] randomSequence = { 3 };

        state = new WaitBeforeNewGemsPairState(environment, environment.getTimer().getTime());
        controller = GridController.create(environment, new InputReactor(null, 0, 0), new MockRandomGenerator(randomSequence), new Point(0, 0));
        grid = controller.getGrid();
    }


    public void testCurrentStateWrongName()
    {
        assertFalse(state.isCurrentState("asdasdasd"));
    }


    public void testCurrentStateRightName()
    {
        assertTrue(state.isCurrentState("WaitBeforeNewGemsPair"));
    }


    public void testInitState()
    {
        state = new WaitBeforeNewGemsPairState(environment, environment.getTimer().getTime());
        makeAllGemsFall();
        for (int t = 1; t <= getNewGemDelay(); t++)
        {
            assertTrue(state.isCurrentState("WaitBeforeNewGemsPair"));
            assertNull("pivot gem must not be setted", controller.getGemsPair().getPivot());

            environment.getTimer().advance(1);
            state = state.update(environment.getTimer().getTime(), controller, new ScoreCalculator(0), null);
        }

        assertTrue("not correct state returned", state.isCurrentState("GemsPairOnControl"));
        assertEquals("not correct gravity", getDeltaYGravity(), grid.getActualGravity(), 0.001f);
        assertNotNull("pivot gem must be setted", controller.getGemsPair().getPivot());
    }


    public void testInitState2()
    {
        state = new WaitBeforeNewGemsPairState(environment, 12345);
        ((MockTimer)environment.getTimer()).setTime(0);
        environment.getTimer().advance(12345 + getNewGemDelay() - 1);
        makeAllGemsFall();

        state = state.update(environment.getTimer().getTime(), controller, new ScoreCalculator(0), null);
        assertTrue(state.isCurrentState("WaitBeforeNewGemsPair"));
        assertNull("pivot gem must not be setted", controller.getGemsPair().getPivot());

        environment.getTimer().advance(1);
        state = state.update(environment.getTimer().getTime(), controller, null, null);
        assertTrue("not correct state returned", state.isCurrentState("GemsPairOnControl"));
        assertEquals("not correct gravity", getDeltaYGravity(), grid.getActualGravity(), 0.001f);
        assertNotNull("pivot gem must be setted", controller.getGemsPair().getPivot());
    }


    public void testPassToGameOverState()
    {
        state = new WaitBeforeNewGemsPairState(environment, environment.getTimer().getTime());
        makeAllGemsFall();
        for (int row = 11; row >= 0; row--)
        {
            insertAndUpdate(createGem(DIAMOND), row, 4);
        }
        assertFalse(controller.isGameOver());

        for (int t = 1; t <= getNewGemDelay(); t++)
        {
            assertTrue(state.isCurrentState("WaitBeforeNewGemsPair"));
            environment.getTimer().advance(1);
            state = state.update(environment.getTimer().getTime(), controller, scoreCalculator, null);
        }

        assertTrue("not correct state returned", state.isCurrentState("GameOver"));
    }


    public void testThisStateIsReactive()
    {
        MockInputReactor input = new MockInputReactor();
        state.reactToInput(input, 0);
        assertTrue(input.hasReacted());
    }

}
