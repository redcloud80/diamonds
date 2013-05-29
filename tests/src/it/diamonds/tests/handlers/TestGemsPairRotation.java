package it.diamonds.tests.handlers;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.grid.Cell;
import it.diamonds.tests.GemsPairTestCase;


public class TestGemsPairRotation extends GemsPairTestCase
{

    public void testRotationWithDestinationOccupied()
    {
        Droppable droppable = createGem(DroppableColor.DIAMOND);
        Cell cell = Cell.create(1, 5);
        droppable.getRegion().setRow(cell.getRow());
        droppable.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(droppable);

        gemsPair.rotateClockwise();

        assertSame("slave must not move", gemsPair.getSlave(), grid.getDroppableAt(Cell.create(0, 4)));
    }


    public void testGemsPairCanRotateClockwise()
    {
        assertSame("slaveGem should have been up", grid.getDroppableAt(Cell.create(0, 4)), gemsPair.getSlave());

        gemsPair.rotateClockwise();
        assertSame("slaveGem didn't rotate", grid.getDroppableAt(Cell.create(1, 5)), gemsPair.getSlave());

        gemsPair.rotateClockwise();
        assertSame("slaveGem didn't rotate", grid.getDroppableAt(Cell.create(2, 4)), gemsPair.getSlave());

        gemsPair.rotateClockwise();
        assertSame("slaveGem didn't rotate", grid.getDroppableAt(Cell.create(1, 3)), gemsPair.getSlave());

        gemsPair.rotateClockwise();
        assertSame("slaveGem didn't rotate", grid.getDroppableAt(Cell.create(0, 4)), gemsPair.getSlave());
    }


    public void testGemsPairCanRotateCounterclockwise()
    {
        assertSame("slaveGem should have been up", grid.getDroppableAt(Cell.create(0, 4)), gemsPair.getSlave());

        gemsPair.rotateCounterClockwise();
        assertSame("slaveGem didn't rotate", grid.getDroppableAt(Cell.create(1, 3)), gemsPair.getSlave());

        gemsPair.rotateCounterClockwise();
        assertSame("slaveGem didn't rotate", grid.getDroppableAt(Cell.create(2, 4)), gemsPair.getSlave());

        gemsPair.rotateCounterClockwise();
        assertSame("slaveGem didn't rotate", grid.getDroppableAt(Cell.create(1, 5)), gemsPair.getSlave());

        gemsPair.rotateCounterClockwise();
        assertSame("slaveGem didn't rotate", grid.getDroppableAt(Cell.create(0, 4)), gemsPair.getSlave());
    }

}
