package it.diamonds.droppable.types;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.gems.FlashingGem;
import it.diamonds.engine.Environment;


final class FlashingDroppableType extends DroppableType
{
    public Droppable createInstance(Environment environment, DroppableColor color, int gemAnimationDelay, int animationUpdateRate, long lastUpdateTimeStamp)
    {
        return new FlashingGem(environment.getEngine(), 0, animationUpdateRate);
    }
}
