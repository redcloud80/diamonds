package it.diamonds.tests.grid;


import it.diamonds.droppable.Droppable;
import it.diamonds.droppable.DroppableColor;
import it.diamonds.grid.Cell;
import it.diamonds.tests.GridTestCase;


public class TestGridCells extends GridTestCase
{
    private int rows;

    private int columns;

    private Droppable gem1;

    private Droppable gem2;


    public void setUp()
    {
        super.setUp();
        gem1 = createGem(DroppableColor.DIAMOND);
        gem2 = createGem(DroppableColor.DIAMOND);

        rows = grid.getNumberOfRows();
        columns = grid.getNumberOfColumns();
    }


    public void testIsValidCell()
    {
        assertTrue("(0, 0) must be valid cell", grid.isValidCell(Cell.create(0, 0)));
        assertTrue("(0, " + (columns - 1) + ") must be valid cell", grid.isValidCell(Cell.create(0, columns - 1)));
        assertTrue("(" + (rows - 1) + ", 0) must be valid cell", grid.isValidCell(Cell.create(rows - 1, 0)));

        assertFalse("(0, " + columns + ") must not be valid cell", grid.isValidCell(Cell.create(0, columns)));
        assertFalse("(" + rows + ", 0) must not be valid cell", grid.isValidCell(Cell.create(rows, 0)));
    }


    public void testInsertion()
    {
        Cell cell = Cell.create(1, 1);

        assertFalse("Gem at (1, 1) before insertion", (!grid.isCellFree(cell)));
        Cell cell1 = Cell.create(1, 1);

        gem1.getRegion().setRow(cell1.getRow());
        gem1.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(gem1);

        assertTrue("insertion failed at 1, 1", (!grid.isCellFree(cell)));
    }


    public void testTwoInsertions()
    {
        Cell cell1 = Cell.create(1, 2);
        gem1.getRegion().setRow(cell1.getRow());
        gem1.getRegion().setColumn(cell1.getColumn());

        grid.insertDroppable(gem1);

        Cell cell = Cell.create(3, 0);

        assertFalse("Gem at (3, 0) before insertion", (!grid.isCellFree(cell)));
        Cell cell2 = Cell.create(3, 0);
        gem2.getRegion().setRow(cell2.getRow());
        gem2.getRegion().setColumn(cell2.getColumn());

        grid.insertDroppable(gem2);
        assertTrue("insertion failed at (3, 0) ", (!grid.isCellFree(cell)));
    }


    public void testRetrieval()
    {
        Cell cell = Cell.create(1, 0);
        gem1.getRegion().setRow(cell.getRow());
        gem1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem1);
        assertSame("Gem retrieval failed", gem1, grid.getDroppableAt(Cell.create(1, 0)));
    }


    public void testAddGemToANotEmptyCell()
    {
        try
        {
            Cell cell = Cell.create(5, 5);
            gem1.getRegion().setRow(cell.getRow());
            gem1.getRegion().setColumn(cell.getColumn());

            grid.insertDroppable(gem1);
            Cell cell1 = Cell.create(5, 5);
            gem2.getRegion().setRow(cell1.getRow());
            gem2.getRegion().setColumn(cell1.getColumn());

            grid.insertDroppable(gem2);
            fail("Insertion of a gem in a not empty cell must not be allowed");
        }
        catch (IllegalArgumentException e)
        {
            ;
        }
    }


    public void testSameInsertion()
    {

        try
        {
            Cell cell = Cell.create(4, 3);
            gem1.getRegion().setRow(cell.getRow());
            gem1.getRegion().setColumn(cell.getColumn());

            grid.insertDroppable(gem1);
            Cell cell1 = Cell.create(4, 4);
            gem1.getRegion().setRow(cell1.getRow());
            gem1.getRegion().setColumn(cell1.getColumn());

            grid.insertDroppable(gem1);
            fail("same gem inserted at different positions");
        }
        catch (IllegalArgumentException e)
        {
            ;
        }
    }


    public void testNullInsertion()
    {
        try
        {
            grid.insertDroppable(null);
            fail("null gem inserted");
        }
        catch (IllegalArgumentException e)
        {
            ;
        }
    }


    public void testOverflow()
    {
        try
        {
            Cell cell = Cell.create(100, 100);
            gem1.getRegion().setRow(cell.getRow());
            gem1.getRegion().setColumn(cell.getColumn());

            grid.insertDroppable(gem1);
            fail("wrong parameters not rejected");
        }
        catch (IllegalArgumentException e)
        {
            ;
        }
    }


    public void testRemove()
    {
        Cell cell = Cell.create(1, 1);
        gem1.getRegion().setRow(cell.getRow());
        gem1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem1);
        grid.removeDroppable(gem1);

        assertFalse("Gem must be removed", (!grid.isCellFree(Cell.create(1, 1))));
    }


    public void testGemRegionAfterInsertion()
    {
        Cell cell = Cell.create(1, 2);
        gem1.getRegion().setRow(cell.getRow());
        gem1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem1);

        assertEquals(1, gem1.getRegion().getTopRow());
        assertEquals(2, gem1.getRegion().getLeftColumn());
    }


    public void testFreeCell()
    {
        assertTrue(grid.isCellFree(Cell.create(0, 0)));
    }


    public void testFreeCellInvalidCell()
    {
        assertFalse(grid.isCellFree(Cell.create(rows, columns)));
    }


    public void testFreeCellNotEmptyCell()
    {
        Cell cell = Cell.create(0, 0);
        gem1.getRegion().setRow(cell.getRow());
        gem1.getRegion().setColumn(cell.getColumn());

        grid.insertDroppable(gem1);
        assertFalse(grid.isCellFree(Cell.create(0, 0)));
    }
}
