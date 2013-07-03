package it.diamonds.tests.handlers;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.grid.Cell;
import it.diamonds.tests.GemsPairTestCase;


public class TestGemsPairMirroring extends GemsPairTestCase
{

    public void testWithDestinationDestinationOccupied()
    {
        Droppable droppable = createGem(DroppableColor.DIAMOND);
        Cell cell = Cell.create(2, 4);
        droppable.getRegion().setRow(cell.getRow());
        droppable.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(droppable);

        gemsPair.mirrorSlave();

        assertSame("slave must not move", gemsPair.getSlave(), grid.getDroppableAt(Cell.create(0, 4)));
    }


    public void testCanMirrorSlaveGem()
    {
        assertSame("slaveGem should have been up", grid.getDroppableAt(Cell.create(0, 4)), gemsPair.getSlave());

        gemsPair.mirrorSlave();
        assertSame("slaveGem didn't rotate", grid.getDroppableAt(Cell.create(2, 4)), gemsPair.getSlave());

        gemsPair.mirrorSlave();
        assertSame("slaveGem didn't rotate", grid.getDroppableAt(Cell.create(0, 4)), gemsPair.getSlave());

        gemsPair.rotateClockwise();
        assertSame("slaveGem didn't rotate", grid.getDroppableAt(Cell.create(1, 5)), gemsPair.getSlave());

        gemsPair.mirrorSlave();
        assertSame("slaveGem didn't rotate", grid.getDroppableAt(Cell.create(1, 3)), gemsPair.getSlave());

        gemsPair.mirrorSlave();
        assertSame("slaveGem didn't rotate", grid.getDroppableAt(Cell.create(1, 5)), gemsPair.getSlave());
    }

}
