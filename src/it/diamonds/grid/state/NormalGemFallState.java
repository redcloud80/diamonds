package it.diamonds.grid.state;


import it.diamonds.engine.Environment;


public class NormalGemFallState implements GemFallStrategy
{
    public AbstractControllerState getState(Environment environment, long timer)
    {
        return new WaitNextCrushState(environment, timer);
    }

}
