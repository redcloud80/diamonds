package it.diamonds.grid.state;


import it.diamonds.ScoreCalculator;
import it.diamonds.StoneCalculator;
import it.diamonds.droppable.Pattern;
import it.diamonds.engine.Environment;
import it.diamonds.grid.Grid;
import it.diamonds.grid.GridController;


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


    public boolean isCurrentState(String stateName)
    {
        return stateName.equals("Crush");
    }


    public AbstractControllerState update(long timer, GridController gridController, ScoreCalculator scoreCalculator, StoneCalculator stoneCalculator)
    {

        Grid grid = gridController.getGrid();

        grid.updateBigGems();

        grid.updateCrushes(scoreCalculator, stoneCalculator);

        if (!gridController.droppedGemWithoutGemsPairCanMoveDown())
        {
            scoreCalculator.closeChain();
            return stoneFallState.update(timer, gridController, scoreCalculator, stoneCalculator);
        }

        return gemFallState;
    }

}
