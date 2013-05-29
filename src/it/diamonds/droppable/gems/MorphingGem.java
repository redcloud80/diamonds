package it.diamonds.droppable.gems;


import static it.diamonds.droppable.types.DroppableType.GEM;
import it.diamonds.droppable.AbstractSingleDroppable;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.DroppableDescription;
import it.diamonds.droppable.DroppableFactory;
import it.diamonds.droppable.types.DroppableType;
import it.diamonds.engine.Environment;
import it.diamonds.engine.video.AnimationDescription;
import it.diamonds.grid.GridController;


public class MorphingGem extends AbstractSingleDroppable
{
    private transient DroppableFactory gemFactory;

    private long lastUpdateTimeStamp;

    private int animationUpdateRate;


    public MorphingGem(Environment environment, DroppableColor color, int animationDelay, int animationUpdateRate, long lastUpdateTimeStamp)
    {
        super(environment.getEngine(), new DroppableDescription(DroppableType.MORPHING_GEM, color), new AnimationDescription(8, animationDelay, animationUpdateRate));
        gemFactory = new DroppableFactory(environment);
        this.animationUpdateRate = animationUpdateRate;
        this.lastUpdateTimeStamp = lastUpdateTimeStamp;
    }


    public Droppable transform(GridController gridController)
    {
        int currentFrame = getAnimatedSprite().getCurrentFrame();
        if (currentFrame >= 7)
        {
            gridController.decreaseMorphingGemCount();
            Droppable newGem = gemFactory.create(GEM, getHiddenColor(), 0);
            newGem.drop();
            return newGem;
        }
        return this;
    }


    public void updateAnimation(long timer)
    {
        if (timer < lastUpdateTimeStamp + animationUpdateRate)
        {
            return;
        }
        int currentFrame = getAnimatedSprite().getCurrentFrame();
        getAnimatedSprite().setCurrentFrame(currentFrame + 1);
    }
}
