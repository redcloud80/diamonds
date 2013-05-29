package it.diamonds.droppable;


import it.diamonds.engine.Engine;
import it.diamonds.engine.audio.Sound;
import it.diamonds.engine.video.AnimatedSprite;
import it.diamonds.engine.video.AnimationDescription;
import it.diamonds.grid.Grid;
import it.diamonds.grid.Region;
import it.diamonds.renderer.DroppableRenderer;


public abstract class AbstractSingleDroppable extends AbstractDroppable
{
    private static Sound dropSound;

    private boolean stopped;


    protected AbstractSingleDroppable(Engine engine, DroppableDescription droppableDescription, AnimationDescription animationDescription)
    {
        super(droppableDescription);

        AnimatedSprite animatedSprite = new DroppableRenderer(engine, droppableDescription, animationDescription);

        setAnimatedSprite(animatedSprite);
    }


    public int getScore()
    {
        return 0;
    }


    private void playDropSound()
    {
        if (null != dropSound)
        {
            dropSound.play();
        }
    }


    public static void setDropSound(Sound sound)
    {
        if (sound == null)
        {
            throw new NullPointerException();
        }

        dropSound = sound;
    }


    public boolean isFalling()
    {
        return !stopped;
    }


    public void drop()
    {
        if (isFalling())
        {
            stopped = true;
            playDropSound();
        }
    }


    private Region getRegionToBottom(int numOfRows)
    {

        return new Region(getRegion().getLeftColumn(), getRegion().getTopRow(), getRegion().getWidth(), numOfRows);
    }


    private boolean willMoveDown(Grid grid)
    {

        DroppableList droppableInTheColumn = grid.getDroppablesInArea(getRegionToBottom(grid.getNumberOfRows()));

        boolean canMoveDown = false;
        for (Droppable droppable : droppableInTheColumn)
        {
            canMoveDown |= droppable.canMoveDown(grid);
        }
        return canMoveDown;
    }


    public void moveDown(Grid grid)
    {
        super.moveDown(grid);
        if (!willMoveDown(grid))
        {
            drop();
        }
    }
}
