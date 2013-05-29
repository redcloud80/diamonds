package it.diamonds.droppable.types;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.gems.Gem;
import it.diamonds.engine.Environment;


class GemDroppableType extends DroppableType
{
    public Droppable createInstance(Environment environment, DroppableColor color, int gemAnimationDelay, int animationUpdateRate, long lastUpdateTimeStamp)
    {
        return new Gem(environment.getEngine(), color, gemAnimationDelay, animationUpdateRate);
    }
}
