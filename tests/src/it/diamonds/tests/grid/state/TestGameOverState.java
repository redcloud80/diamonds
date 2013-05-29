package it.diamonds.tests.grid.state;

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
        
        state = new GameOverState();
    }


    public void testReturnedState()
    {
        state = state.update(environment.getTimer().getTime(), controller, scoreCalculator, null);
        assertNotNull((GameOverState)state);
    }


    public void testThisStateIsNotReactive()
    {
        MockInputReactor input = new MockInputReactor();
        state.reactToInput(input, 0);
        assertFalse(input.hasReacted());
    }

}
