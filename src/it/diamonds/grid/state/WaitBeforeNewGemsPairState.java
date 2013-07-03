package it.diamonds.grid.state;


import it.diamonds.ScoreCalculator;
import it.diamonds.StoneCalculator;
import it.diamonds.engine.Environment;
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


    public boolean isCurrentState(String stateName)
    {
        return stateName.equals("WaitBeforeNewGemsPair");
    }


    public AbstractControllerState update(long timer, GridController gridController, ScoreCalculator scoreCalculator, StoneCalculator stoneCalculator)
    {
        if (timeBase + newGemDelay <= timer)
        {
            if (gridController.isCenterColumnFull())
            {
                return new GameOverState();
            }
            gridController.insertNewGemsPair();

            return new GemsPairOnControlState(environment);
        }
        return this;
    }

}
