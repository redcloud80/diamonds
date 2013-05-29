package it.diamonds.grid.state;


import it.diamonds.ScoreCalculator;
import it.diamonds.StoneCalculator;
import it.diamonds.engine.Environment;
import it.diamonds.grid.GridController;


public class WaitNextCrushState extends AbstractControllerState
{

    private long allGemsHaltedTimeStamp;

    private int delayBeforeNextCrush;

    private CrushState crushState;


    public WaitNextCrushState(Environment environment, long time)
    {
        this.allGemsHaltedTimeStamp = time;
        this.delayBeforeNextCrush = environment.getConfig().getInteger("DelayBetweenCrushes");
        this.crushState = new CrushState(environment);
    }


    public AbstractControllerState update(long timer, GridController gridController, ScoreCalculator scoreCalculator, StoneCalculator stoneCalculator)
    {
        if (allGemsHaltedTimeStamp + delayBeforeNextCrush <= timer)
        {
            return crushState.update(timer, gridController, scoreCalculator, stoneCalculator);
        }

        return this;
    }

}
