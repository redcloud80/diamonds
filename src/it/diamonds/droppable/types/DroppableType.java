package it.diamonds.droppable.types;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.engine.Environment;


public abstract class DroppableType
{
    public static final DroppableType GEM = new GemDroppableType();

    public static final DroppableType BIG_GEM = new GemDroppableType();

    public static final DroppableType CHEST = new ChestDroppableType();

    public static final DroppableType STONE = new StoneDroppableType();

    public static final DroppableType FLASHING_GEM = new FlashingDroppableType();

    public static final DroppableType MORPHING_GEM = new MorphingGemDroppableType();


    protected DroppableType()
    {
    }


    public abstract Droppable createInstance(Environment environment, DroppableColor color, int gemAnimationDelay, int animationUpdateRate, long lastUpdateTimeStamp);
}
