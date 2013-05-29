package it.diamonds.droppable.types;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.gems.MorphingGem;
import it.diamonds.engine.Environment;


final class MorphingGemDroppableType extends DroppableType
{
    public Droppable createInstance(Environment environment, DroppableColor color, int gemAnimationDelay, int animationUpdateRate, long lastUpdateTimeStamp)
    {
        return new MorphingGem(environment, color, gemAnimationDelay, animationUpdateRate, lastUpdateTimeStamp);
    }
}
