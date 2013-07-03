package it.diamonds.tests.grid.state;


import it.diamonds.GameLoop;
import it.diamonds.PlayField;
import it.diamonds.grid.state.AbstractControllerState;
import it.diamonds.grid.state.GameOverState;
import it.diamonds.tests.GridTestCase;
import it.diamonds.tests.mocks.MockInputReactor;


public class TestGameOverState extends GridTestCase
{
    private AbstractControllerState state;


    public void setUp()
    {
        super.setUp();

        GameLoop loop = new GameLoop(environment);
        PlayField field = loop.getPlayFieldOne();
        controller = field.getGridController();
        state = new GameOverState();
        grid = controller.getGrid();
    }


    public void testCurrentStateWrongName()
    {
        assertFalse(state.isCurrentState("asdasdasd"));
    }


    public void testCurrentStateRightName()
    {
        assertTrue(state.isCurrentState("GameOver"));
    }


    public void testReturnedState()
    {
        state = state.update(environment.getTimer().getTime(), controller, scoreCalculator, null);
        assertTrue(state.isCurrentState("GameOver"));
    }


    public void testThisStateIsNotReactive()
    {
        MockInputReactor input = new MockInputReactor();
        state.reactToInput(input, 0);
        assertFalse(input.hasReacted());
    }

}
