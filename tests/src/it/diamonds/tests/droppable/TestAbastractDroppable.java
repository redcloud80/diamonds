package it.diamonds.tests.droppable;


import it.diamonds.droppable.AbstractDroppable;
import it.diamonds.engine.Point;
import junit.framework.TestCase;


public class TestAbastractDroppable extends TestCase
{
    private AbstractDroppable droppable = null;


    public void setUp()
    {
        droppable = new MockAbstractDroppable();
    }


    public void testGetScreenPositionReturnsDefaultZeroPoint()
    {
        Point zeroPoint = new Point(0.f, 0.f);
        assertEquals(zeroPoint, droppable.getPositionInGridLocalSpace());
    }

}
