package it.diamonds.grid.state;


import it.diamonds.ScoreCalculator;
import it.diamonds.StoneCalculator;
import it.diamonds.engine.Environment;
import it.diamonds.grid.GridController;
import it.diamonds.grid.iteration.TransformingIteration;


public class StoneTurnState extends AbstractControllerState
{
    private Environment environment;


    public StoneTurnState(Environment environment)
    {
        this.environment = environment;
    }


    public AbstractControllerState update(long timer, GridController gridController, ScoreCalculator scoreCalculator, StoneCalculator stoneCalculator)
    {
        TransformingIteration iteration = new TransformingIteration(gridController);
        gridController.getGridRenderer().getGrid().runIteration(iteration);

        if (iteration.areThereMorphingGems())
        {
            return this;
        }

        return new CrushState(environment).update(timer, gridController, scoreCalculator, stoneCalculator);
    }

}
