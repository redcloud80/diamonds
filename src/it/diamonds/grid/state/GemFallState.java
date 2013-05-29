package it.diamonds.grid.state;


import it.diamonds.ScoreCalculator;
import it.diamonds.StoneCalculator;
import it.diamonds.engine.Environment;
import it.diamonds.grid.Grid;
import it.diamonds.grid.GridController;
import it.diamonds.grid.iteration.CanMoveDownQuery;


public class GemFallState extends AbstractControllerState
{
    private Environment environment;

    private GemFallStrategy gemFallStrategy;


    public GemFallState(Environment environment)
    {
        this.environment = environment;
        this.gemFallStrategy = new NormalGemFallState();
    }


    public AbstractControllerState update(long timer, GridController gridController, ScoreCalculator scoreCalculator, StoneCalculator stoneCalculator)
    {
        Grid grid = gridController.getGridRenderer().getGrid();

        grid.setStrongestGravity();
        grid.updateFalls();

        CanMoveDownQuery action = new CanMoveDownQuery(grid);
        grid.runIteration(action);
        boolean droppedGemWithoutGemsPairCanMoveDown = action.getResult();
        
        if (droppedGemWithoutGemsPairCanMoveDown)
        {
            return this;
        }

        grid.setNormalGravity();
        return gemFallStrategy.getState(environment, timer).update(timer, gridController, scoreCalculator, null);
    }
}
