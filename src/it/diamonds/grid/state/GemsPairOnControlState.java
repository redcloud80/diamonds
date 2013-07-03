package it.diamonds.grid.state;


import it.diamonds.ScoreCalculator;
import it.diamonds.StoneCalculator;
import it.diamonds.droppable.pair.DroppablePair;
import it.diamonds.engine.Environment;
import it.diamonds.grid.Grid;
import it.diamonds.grid.GridController;


public class GemsPairOnControlState extends AbstractReactiveState
{
    private Environment environment;


    public GemsPairOnControlState(Environment environment)
    {
        this.environment = environment;
    }


    public boolean isCurrentState(String stateName)
    {
        return stateName.equals("GemsPairOnControl");
    }


    public AbstractControllerState update(long timer, GridController gridController, ScoreCalculator scoreCalculator, StoneCalculator stoneCalculator)
    {
        Grid grid = gridController.getGrid();

        scoreCalculator.resetChainCounter();

        DroppablePair gemsPair = gridController.getGemsPair();
        gemsPair.update(timer);

        if (gemsPair.oneDroppableIsNotFalling())
        {
            grid.setStrongestGravity();
        }
        else if (gemsPair.bothDroppablesAreNotFalling())
        {
            grid.setNormalGravity();

            grid.updateStones();
            gemsPair.setNoPivot();
            gemsPair.setNoSlave();

            return new StoneTurnState(environment).update(timer, gridController, scoreCalculator, stoneCalculator);
        }

        return this;
    }

}
