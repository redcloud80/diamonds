package it.diamonds.tests.mocks;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.DroppableGenerator;
import it.diamonds.droppable.gems.Gem;
import it.diamonds.engine.Engine;


public class MockDroppableGenerator implements DroppableGenerator
{
    private Engine engine;


    public MockDroppableGenerator(Engine engine)
    {
        this.engine = engine;
    }


    public Droppable extract()
    {
        return new Gem(engine, DroppableColor.DIAMOND, 3500, 0);
    }


    public Droppable getGemAt(int index)
    {
        return extract();
    }
}
