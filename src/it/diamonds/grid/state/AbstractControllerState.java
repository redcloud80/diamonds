package it.diamonds.grid.state;


import it.diamonds.ScoreCalculator;
import it.diamonds.StoneCalculator;
import it.diamonds.engine.input.InputReactorInterface;
import it.diamonds.grid.GridController;


public abstract class AbstractControllerState
{
    public abstract AbstractControllerState update(long timer, GridController gridController, ScoreCalculator scoreCalculator, StoneCalculator stoneCalculator);


    public void reactToInput(InputReactorInterface input, long timer)
    {
        ;
    }

}
