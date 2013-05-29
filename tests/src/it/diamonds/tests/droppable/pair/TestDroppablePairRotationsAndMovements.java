package it.diamonds.tests.droppable.pair;

import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.pair.Direction;
import it.diamonds.grid.Cell;
import it.diamonds.tests.GemsPairTestCase;

public class TestDroppablePairRotationsAndMovements extends GemsPairTestCase
{

    public void setUp()
    {
        super.setUp();
    }
    
    private void movePairToHalfWayInCell(Droppable pivot, Droppable slave)
    {
        float originalPivotY = pivot.getPositionInGridLocalSpace().getY();
        float originalSlaveY = slave.getPositionInGridLocalSpace().getY();
        
        pivot.getPositionInGridLocalSpace().setY(originalPivotY + (Cell.SIZE_IN_PIXELS / 4.0f));
        slave.getPositionInGridLocalSpace().setY(originalSlaveY + (Cell.SIZE_IN_PIXELS / 4.0f));
    }
    
    public void testDroppablesInPairAreNotAlignedAfterRotations()
    {
        Droppable pivot = gemsPair.getPivot();
        Droppable slave = gemsPair.getSlave();
        
        movePairToHalfWayInCell(pivot, slave);
        
        float halfWayPivotY = pivot.getPositionInGridLocalSpace().getY();
        float halfWaySlaveY = slave.getPositionInGridLocalSpace().getY();
        
        gemsPair.rotateClockwise();
        gemsPair.rotateCounterClockwise();
        
        assertEquals(halfWayPivotY, pivot.getPositionInGridLocalSpace().getY());
        assertEquals(halfWaySlaveY, slave.getPositionInGridLocalSpace().getY());
    }
    
    public void testDroppablesInPairAreNotAlignedAfterTranslations()
    {
        Droppable pivot = gemsPair.getPivot();
        Droppable slave = gemsPair.getSlave();
        
        movePairToHalfWayInCell(pivot, slave);
        
        float halfWayPivotY = pivot.getPositionInGridLocalSpace().getY();
        float halfWaySlaveY = slave.getPositionInGridLocalSpace().getY();
        
        gemsPair.move(Direction.GO_LEFT);
        
        assertEquals(halfWayPivotY, pivot.getPositionInGridLocalSpace().getY());
        assertEquals(halfWaySlaveY, slave.getPositionInGridLocalSpace().getY());
    }
}

