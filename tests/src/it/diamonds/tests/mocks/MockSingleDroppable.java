package it.diamonds.tests.mocks;


import it.diamonds.droppable.AbstractSingleDroppable;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.DroppableDescription;
import it.diamonds.droppable.types.DroppableType;
import it.diamonds.engine.Engine;
import it.diamonds.engine.video.AnimationDescription;
import it.diamonds.grid.GridController;


public class MockSingleDroppable extends AbstractSingleDroppable
{

    public MockSingleDroppable(Engine engine, DroppableType type, DroppableColor color, int animationDelay, int animationUpdateRate)
    {
        super(engine, new DroppableDescription(type, color), new AnimationDescription(6, animationDelay, animationUpdateRate));
    }


    public static MockSingleDroppable create(Engine engine)
    {
        return new MockSingleDroppable(engine, DroppableType.GEM, DroppableColor.DIAMOND, 0, 0);
    }


    public Droppable transform(GridController gridController)
    {
        return null;
    }
}
