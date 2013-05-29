package it.diamonds.grid.state;


import it.diamonds.ScoreCalculator;
import it.diamonds.StoneCalculator;
import it.diamonds.engine.Environment;
import it.diamonds.grid.Grid;
import it.diamonds.grid.GridController;


public class WaitBeforeNewGemsPairState extends AbstractReactiveState
{
    private int newGemDelay;

    private long timeBase;

    private Environment environment;


    public WaitBeforeNewGemsPairState(Environment environment, long time)
    {
        this.newGemDelay = environment.getConfig().getInteger("NewGemDelay");
        this.environment = environment;
        this.timeBase = time;
    }


    public AbstractControllerState update(long timer, GridController gridController, ScoreCalculator scoreCalculator, StoneCalculator stoneCalculator)
    {
        if (timeBase + newGemDelay > timer)
        {
            return this;
        }
        
        final Grid grid = gridController.getGridRenderer().getGrid();
        if (grid.isColumnFull(4))
        {
            return new GameOverState();
        }
        gridController.insertNewGemsPair(grid);

        return new GemsPairOnControlState(environment);
    }

}
