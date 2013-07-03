package it.diamonds.droppable.gems;


import it.diamonds.droppable.AbstractSingleDroppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.DroppableDescription;
import it.diamonds.droppable.types.DroppableType;
import it.diamonds.engine.Engine;
import it.diamonds.engine.video.AnimationDescription;


public final class Gem extends AbstractSingleDroppable
{
    public Gem(Engine engine, DroppableColor color, int animationDelay, int animationUpdateRate)
    {
        super(engine, new DroppableDescription(DroppableType.GEM, color), new AnimationDescription(6, animationDelay, animationUpdateRate));
    }


    public int getScore()
    {
        return super.getColor().getScore();
    }


    public boolean canBeAddedToBigGem(DroppableColor color)
    {
        return getColor().equals(color);
    }

}
