package it.diamonds.tests;


import it.diamonds.StoneCalculator;
import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.gems.BigGem;
import it.diamonds.droppable.types.DroppableType;
import it.diamonds.engine.AbstractEngine;
import it.diamonds.tests.mocks.MockEngine;
import it.diamonds.tests.mocks.MockSingleDroppable;
import junit.framework.TestCase;


public class TestStoneCalculator extends TestCase
{
    private StoneCalculator stoneCalculator;

    private AbstractEngine engine;


    public void setUp()
    {
        engine = new MockEngine(10, 10);
        stoneCalculator = new StoneCalculator();
    }


    public void testCounterIsZeroAfterCreation()
    {
        assertSame("Counter must be zero", stoneCalculator.getCounter(), 0);
    }


    public void testAddingOneSingleGem()
    {
        Droppable droppable = new MockSingleDroppable(engine, DroppableType.GEM, DroppableColor.DIAMOND, 0, 0);

        stoneCalculator.addDroppable(droppable);

        assertSame("Counter must be 1", stoneCalculator.getCounter(), 1);
    }


    public void testAddingTwoSingleGems()
    {
        Droppable droppable1 = new MockSingleDroppable(engine, DroppableType.GEM, DroppableColor.DIAMOND, 0, 0);
        Droppable droppable2 = new MockSingleDroppable(engine, DroppableType.GEM, DroppableColor.DIAMOND, 0, 0);

        stoneCalculator.addDroppable(droppable1);
        stoneCalculator.addDroppable(droppable2);

        assertSame("Counter must be 2", stoneCalculator.getCounter(), 2);
    }


    public void testAddingOneBigGem()
    {
        Droppable bigGem = new BigGem(DroppableColor.DIAMOND, engine);

        stoneCalculator.addDroppable(bigGem);

        assertSame("Counter must be 8", stoneCalculator.getCounter(), 8);
    }


    public void testAddingTwoBigGems()
    {
        Droppable bigGem1 = new BigGem(DroppableColor.DIAMOND, engine);
        Droppable bigGem2 = new BigGem(DroppableColor.DIAMOND, engine);

        stoneCalculator.addDroppable(bigGem1);
        stoneCalculator.addDroppable(bigGem2);

        assertSame("Counter must be 16", stoneCalculator.getCounter(), 16);
    }


    public void testAddingChest()
    {
        Droppable chest = new MockSingleDroppable(engine, DroppableType.CHEST, DroppableColor.DIAMOND, 0, 0);

        stoneCalculator.addDroppable(chest);

        assertSame("Counter must be 0 after adding chest", stoneCalculator.getCounter(), 0);
    }


    public void testAddingFlashingGem()
    {
        Droppable flashing = new MockSingleDroppable(engine, DroppableType.FLASHING_GEM, DroppableColor.NO_COLOR, 0, 0);

        stoneCalculator.addDroppable(flashing);

        assertSame("Counter must be 0 after adding chest", stoneCalculator.getCounter(), 0);
    }


    public void testReset()
    {
        Droppable droppable = new MockSingleDroppable(engine, DroppableType.GEM, DroppableColor.DIAMOND, 0, 0);
        stoneCalculator.addDroppable(droppable);

        stoneCalculator.reset();

        assertSame("Counter must be 0 after reset", stoneCalculator.getCounter(), 0);
    }
}
