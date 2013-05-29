package it.diamonds.handlers;


import it.diamonds.droppable.pair.DroppablePair;
import it.diamonds.engine.input.AbstractEventHandler;
import it.diamonds.engine.input.InputReactor;


public class MirrorSlaveGemCommandHandler extends AbstractEventHandler
{
    private DroppablePair gemsPair;


    public MirrorSlaveGemCommandHandler(DroppablePair gemsPair, long normalDelay, long fastDelay)
    {
        this.gemsPair = gemsPair;

        setFastRepeatDelay(fastDelay);
        setNormalRepeatDelay(normalDelay);
    }


    public void executeWhenPressed(InputReactor inputReactor)
    {
        if (gemsPair.canReactToInput())
        {
            gemsPair.mirrorSlave();
        }
    }


    public void executeWhenReleased(InputReactor inputReactor)
    {
        ;
    }
}
