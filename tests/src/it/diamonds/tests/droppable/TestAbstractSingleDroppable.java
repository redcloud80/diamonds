package it.diamonds.tests.droppable;


import it.diamonds.droppable.AbstractSingleDroppable;
import it.diamonds.engine.Engine;
import it.diamonds.engine.Point;
import it.diamonds.grid.Cell;
import it.diamonds.grid.Grid;
import it.diamonds.tests.EnvironmentTestCase;
import it.diamonds.tests.mocks.MockEngine;
import it.diamonds.tests.mocks.MockSingleDroppable;


public class TestAbstractSingleDroppable extends EnvironmentTestCase
{

    private AbstractSingleDroppable abstractSingleDroppable;

    private AbstractSingleDroppable abstractSingleDroppable2;

    private Grid grid1;

    private Grid grid2;


    public void setUp()
    {
        super.setUp();
        Engine engine = MockEngine.create(0, 0);

        abstractSingleDroppable = MockSingleDroppable.create(engine);
        abstractSingleDroppable2 = MockSingleDroppable.create(engine);
        grid1 = new Grid(environment);
        grid2 = new Grid(environment);
    }


    public void testMoveToCell()
    {
        abstractSingleDroppable.moveToCell(Cell.create(0, 0));

        assertEquals(0, abstractSingleDroppable.getRegion().getTopRow());
        assertEquals(0, abstractSingleDroppable.getRegion().getLeftColumn());
        assertEquals(new Point(0, 0), abstractSingleDroppable.getPositionInGridLocalSpace());

        abstractSingleDroppable.moveToCell(Cell.create(2, 3));
        assertEquals(2, abstractSingleDroppable.getRegion().getTopRow());
        assertEquals(3, abstractSingleDroppable.getRegion().getLeftColumn());
        assertEquals(new Point(Cell.SIZE_IN_PIXELS * 3, Cell.SIZE_IN_PIXELS * 2), abstractSingleDroppable.getPositionInGridLocalSpace());

    }


    public void testMoveToCellForGrids50X100()
    {
        Cell cell = Cell.create(0, 4);
        abstractSingleDroppable.getRegion().setRow(cell.getRow());
        abstractSingleDroppable.getRegion().setColumn(cell.getColumn());

        grid1.insertDroppable(abstractSingleDroppable);

        Point newposition = new Point(abstractSingleDroppable.getPositionInGridLocalSpace().getX()
            + Cell.SIZE_IN_PIXELS * 2, abstractSingleDroppable.getPositionInGridLocalSpace().getY()
            + Cell.SIZE_IN_PIXELS);

        abstractSingleDroppable.moveToCell(Cell.create(1, 6));

        assertEquals(newposition, abstractSingleDroppable.getPositionInGridLocalSpace());
    }


    public void testMoveToCellForGrids450X200()
    {
        Cell cell = Cell.create(0, 4);
        abstractSingleDroppable2.getRegion().setRow(cell.getRow());
        abstractSingleDroppable2.getRegion().setColumn(cell.getColumn());

        grid2.insertDroppable(abstractSingleDroppable2);

        Point newposition2 = new Point(abstractSingleDroppable2.getPositionInGridLocalSpace().getX()
            + Cell.SIZE_IN_PIXELS * 2, abstractSingleDroppable2.getPositionInGridLocalSpace().getY()
            + Cell.SIZE_IN_PIXELS);

        abstractSingleDroppable2.moveToCell(Cell.create(1, 6));

        assertEquals(newposition2, abstractSingleDroppable2.getPositionInGridLocalSpace());
    }


    public void testIsFalling()
    {
        assertTrue(abstractSingleDroppable.isFalling());
    }


    public void testIsNotFallingAfterDrop()
    {
        abstractSingleDroppable.drop();
        assertFalse(abstractSingleDroppable.isFalling());
    }


    public void testMoveDownWithDroppableOnBottomIsNotFalling()
    {
        abstractSingleDroppable.getRegion().setRow(13);
        abstractSingleDroppable.getRegion().setColumn(4);

        grid1.insertDroppable(abstractSingleDroppable);
        abstractSingleDroppable.moveDown(grid1);

        assertFalse(abstractSingleDroppable.isFalling());
    }


    public void testDroppableOnBottomAfterMoveDownIsNotFalling()
    {
        abstractSingleDroppable.getRegion().setRow(13);
        abstractSingleDroppable.getRegion().setColumn(4);

        grid1.insertDroppable(abstractSingleDroppable);

        abstractSingleDroppable.getPositionInGridLocalSpace().setY(abstractSingleDroppable.getPositionInGridLocalSpace().getY()
            - getDeltaYGravity());

        abstractSingleDroppable.moveDown(grid1);

        assertFalse(abstractSingleDroppable.isFalling());
    }


    public void testDroppableNotOnBottomAfterMoveDownIsFalling()
    {
        Cell cell = Cell.create(13, 4);
        abstractSingleDroppable.getRegion().setRow(cell.getRow());
        abstractSingleDroppable.getRegion().setColumn(cell.getColumn());

        grid1.insertDroppable(abstractSingleDroppable);

        abstractSingleDroppable.getPositionInGridLocalSpace().setY(abstractSingleDroppable.getPositionInGridLocalSpace().getY()
            - (getDeltaYGravity() * 2));

        abstractSingleDroppable.moveDown(grid1);

        assertTrue(abstractSingleDroppable.isFalling());
    }
}
