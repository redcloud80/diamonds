package it.diamonds.droppable.gems;


import static it.diamonds.droppable.types.DroppableType.MORPHING_GEM;
import it.diamonds.droppable.AbstractSingleDroppable;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.DroppableDescription;
import it.diamonds.droppable.DroppableFactory;
import it.diamonds.droppable.DroppableList;
import it.diamonds.droppable.types.DroppableType;
import it.diamonds.engine.Environment;
import it.diamonds.engine.video.AnimationDescription;
import it.diamonds.grid.Grid;
import it.diamonds.grid.GridController;


public final class Stone extends AbstractSingleDroppable
{
    private static final int FIRST_FRAME_DELAY = 0;

    private long lastUpdateTimeStamp;

    private transient DroppableFactory gemFactory;


    public Stone(Environment environment, DroppableColor color, int animationUpdateRate)
    {
        super(environment.getEngine(), new DroppableDescription(DroppableType.STONE, color), new AnimationDescription(8, FIRST_FRAME_DELAY, animationUpdateRate));
        gemFactory = new DroppableFactory(environment);
    }


    public void updateAnimation(long timer)
    {
        lastUpdateTimeStamp = timer;
    }


    public DroppableColor getHiddenColor()
    {
        return super.getColor();
    }


    public DroppableColor getColor()
    {
        return DroppableColor.NO_COLOR;
    }


    public void tryToAddToCrushableGems(DroppableList crushableGems, DroppableList optionalCrushableGems, Droppable crushStarter, Grid grid)
    {
        optionalCrushableGems.add(this);
    }


    public void updateTransformation()
    {
        int currentFrame = getAnimatedSprite().getCurrentFrame();

        if (currentFrame < 5)
        {
            getAnimatedSprite().setCurrentFrame(currentFrame + 1);
        }
    }


    public Droppable transform(GridController gridController)
    {
        int frame = getAnimatedSprite().getCurrentFrame();
        if (frame < 5)
        {
            return this;
        }

        Droppable morphingGem = gemFactory.create(MORPHING_GEM, getHiddenColor(), lastUpdateTimeStamp);
        morphingGem.getAnimatedSprite().setCurrentFrame(5);
        gridController.increaseMorphingGemCount();
        morphingGem.drop();
        return morphingGem;
    }
}
