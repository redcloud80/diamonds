package it.diamonds.handlers;


import it.diamonds.droppable.pair.DroppablePair;
import it.diamonds.engine.input.AbstractEventHandler;
import it.diamonds.engine.input.InputReactor;


public class RotateCounterclockwiseCommandHandler extends AbstractEventHandler
{
    private DroppablePair gemsPair;


    public RotateCounterclockwiseCommandHandler(DroppablePair gemsPair, long normalDelay, long fastDelay)
    {
        this.gemsPair = gemsPair;

        setFastRepeatDelay(fastDelay);
        setNormalRepeatDelay(normalDelay);
    }


    public void executeWhenPressed(InputReactor inputReactor)
    {
        if (gemsPair.canReactToInput())
        {
            gemsPair.rotateCounterClockwise();
        }
    }


    public void executeWhenReleased(InputReactor inputReactor)
    {
        ;
    }
}
