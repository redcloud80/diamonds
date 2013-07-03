package it.diamonds.droppable.types;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.gems.Chest;
import it.diamonds.engine.Environment;


final class ChestDroppableType extends DroppableType
{
    public Droppable createInstance(Environment environment, DroppableColor color, int gemAnimationDelay, int animationUpdateRate, long lastUpdateTimeStamp)
    {
        return new Chest(environment.getEngine(), color, gemAnimationDelay, animationUpdateRate);
    }
}
