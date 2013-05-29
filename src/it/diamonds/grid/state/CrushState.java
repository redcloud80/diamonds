package it.diamonds.grid.state;


import it.diamonds.ScoreCalculator;
import it.diamonds.StoneCalculator;
import it.diamonds.droppable.Pattern;
import it.diamonds.engine.Environment;
import it.diamonds.grid.Grid;
import it.diamonds.grid.GridController;
import it.diamonds.grid.iteration.CanMoveDownQuery;


public class CrushState extends AbstractControllerState
{
    private static Pattern pattern = new Pattern();

    private AbstractControllerState stoneFallState;

    private AbstractControllerState gemFallState;


    public CrushState(Environment environment)
    {
        this.stoneFallState = new StoneFallState(environment, pattern);
        this.gemFallState = new GemFallState(environment);
    }


    public AbstractControllerState update(long timer, GridController gridController, ScoreCalculator scoreCalculator, StoneCalculator stoneCalculator)
    {

        Grid grid = gridController.getGridRenderer().getGrid();

        grid.updateBigGems();

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        CanMoveDownQuery action = new CanMoveDownQuery(grid);
        grid.runIteration(action);
        boolean droppedGemWithoutGemsPairCanMoveDown = action.getResult();
        
        if (!droppedGemWithoutGemsPairCanMoveDown)
        {
            scoreCalculator.closeChain();
            return stoneFallState.update(timer, gridController, scoreCalculator, stoneCalculator);
        }

        return gemFallState;
    }

}
