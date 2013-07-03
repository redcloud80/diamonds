package it.diamonds;


import it.diamonds.engine.Environment;
import it.diamonds.engine.input.InputReactor;
import it.diamonds.grid.GridController;
import it.diamonds.grid.state.AbstractControllerState;
import it.diamonds.grid.state.GemsPairOnControlState;


public class GameTurn
{
    private AbstractControllerState currentState;

    private StoneCalculator stoneCalculator = new StoneCalculator();


    public GameTurn(Environment environment)
    {
        this.currentState = new GemsPairOnControlState(environment);
    }


    public boolean isGameOver()
    {
        return currentState.isCurrentState("GameOver");
    }


    public void update(long timer, GridController gridController, ScoreCalculator scoreCalculator)
    {
        currentState = currentState.update(timer, gridController, scoreCalculator, stoneCalculator);
    }


    public void reactToInput(InputReactor inputReactor, long timer)
    {
        currentState.reactToInput(inputReactor, timer);
    }

}
