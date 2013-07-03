package it.diamonds.tests.grid.iteration;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.DroppableList;
import it.diamonds.droppable.gems.FlashingGem;
import it.diamonds.droppable.gems.Gem;
import it.diamonds.droppable.gems.Stone;
import it.diamonds.grid.iteration.DroppableIteration;
import it.diamonds.tests.EnvironmentTestCase;


public class DroppableIterationTestCase extends EnvironmentTestCase
{
    private DroppableList fakeGrid;


    protected void setUp()
    {
        super.setUp();

        fakeGrid = new DroppableList();
    }


    protected Gem createGem(DroppableColor color)
    {
        return new Gem(environment.getEngine(), color, 0, 0);
    }


    protected Stone createStone(DroppableColor color)
    {
        return new Stone(environment, color, 0);
    }


    protected FlashingGem createFlashingGem()
    {
        return new FlashingGem(environment.getEngine(), 0, 0);
    }


    protected void insertIntoFakeGrid(Droppable droppable)
    {
        fakeGrid.add(droppable);
    }


    protected void iterateFakeGrid(DroppableIteration iteration)
    {
        for (Droppable droppable : fakeGrid)
        {
            iteration.executeOn(droppable);
        }
    }

}
