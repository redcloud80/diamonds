package it.diamonds.tests.grid.state;


import it.diamonds.grid.state.AbstractControllerState;
import it.diamonds.grid.state.GemFallStrategy;
import it.diamonds.grid.state.NormalGemFallState;
import it.diamonds.grid.state.WaitNextCrushState;
import it.diamonds.tests.GridTestCase;


public class TestGemFallStateStrategy extends GridTestCase
{
    public void testReturnCrushState()
    {
        GemFallStrategy returnWaitNextCrushState = new NormalGemFallState();
        AbstractControllerState state = returnWaitNextCrushState.getState(environment, 10);

        state = state.update(10 + getDelayBetweenCrushes() - 1, controller, scoreCalculator, null);
        assertNotNull((WaitNextCrushState)state);

        state = state.update(10 + getDelayBetweenCrushes(), controller, scoreCalculator, stoneCalculator);
        try
        {
            state = (WaitNextCrushState)state;
            fail();
        }
        catch(ClassCastException exception)
        {
            return;
        }
    }

}
