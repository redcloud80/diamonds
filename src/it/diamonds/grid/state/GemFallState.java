package it.diamonds.grid.state;


import it.diamonds.ScoreCalculator;
import it.diamonds.StoneCalculator;
import it.diamonds.engine.Environment;
import it.diamonds.grid.Grid;
import it.diamonds.grid.GridController;


public class GemFallState extends AbstractControllerState
{
    private Environment environment;

    private GemFallStrategy gemFallStrategy;


    public GemFallState(Environment environment)
    {
        this.environment = environment;
        this.gemFallStrategy = new NormalGemFallState();
    }


    public boolean isCurrentState(String stateName)
    {
        return stateName.equals("GemFall");
    }


    public AbstractControllerState update(long timer, GridController gridController, ScoreCalculator scoreCalculator, StoneCalculator stoneCalculator)
    {
        Grid grid = gridController.getGrid();

        grid.setStrongestGravity();
        grid.updateFalls();

        if (gridController.droppedGemWithoutGemsPairCanMoveDown())
        {
            return this;
        }

        grid.setNormalGravity();
        return gemFallStrategy.getState(environment, timer).update(timer, gridController, scoreCalculator, null);
    }
}
