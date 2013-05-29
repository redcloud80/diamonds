package it.diamonds.tests.droppable;


import it.diamonds.droppable.AbstractSingleDroppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.droppable.gems.BigGem;
import it.diamonds.engine.Engine;
import it.diamonds.tests.mocks.MockEngine;
import it.diamonds.tests.mocks.MockSingleDroppable;
import junit.framework.TestCase;


public class TestDroppableArea extends TestCase
{

    private BigGem bigGem;

    private AbstractSingleDroppable singleGem;


    public void setUp()
    {
        Engine engine = MockEngine.create(0, 0);

        singleGem = MockSingleDroppable.create(engine);
        bigGem = new BigGem(DroppableColor.DIAMOND, engine);
    }


    public void testGetAreaOfASingelGem()
    {
        assertEquals(1, singleGem.getArea());
    }


    public void testAreaOfABigGem()
    {
        assertEquals(4, bigGem.getArea());
    }


    public void testAreaOfABigGemAfterInsertion()
    {
        singleGem.getRegion().setColumn(2);
        singleGem.getRegion().setRow(1);

        bigGem.getRegion().resizeToContain(singleGem.getRegion());

        assertEquals(6, bigGem.getArea());
    }
}
